package bolangit.test.core;

import bolangit.test.model.Employee;
import bolangit.test.rpc.RPCError;
import bolangit.test.rpc.RPCReq;
import bolangit.test.rpc.RPCResp;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
public class EmployeeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping(path = "/do", consumes = "application/json", produces = "application/json")
    public String add(@RequestBody RPCReq req) {

        RPCResp resp = null;
        try {
            resp = new RPCResp();
            resp.id = req.id;
            switch (req.method) {
                case "add": {
                    Employee emp = JSON.parseObject(req.params, Employee.class);
                    resp.result = add(emp);
                    break;
                }
                case "list": {
                    resp.result = list();
                    break;
                }
                case "update": {
                    Employee emp = JSON.parseObject(req.params, Employee.class);
                    resp.result = update(emp);
                    break;
                }
                case "del": {
                    Employee emp = JSON.parseObject(req.params, Employee.class);
                    resp.result =  del(emp);
                    break;
                }
                default:
                    resp.error = "方法不存在";
                    break;
            }
            return JSON.toJSONString(resp);
        } catch (Exception e) {
            e.printStackTrace();
            RPCError error = new RPCError();
            error.code = -32600;
            error.message = "无效请求";
            return JSON.toJSONString(error);
        }
    }

    public String add(Employee emp) {
        int res = jdbcTemplate.update("insert into employee(id, name,sex,age,salary ) values(?, ?, ?, ?, ?)", emp.id, emp.name, emp.sex, emp.age, emp.salary);
        return String.valueOf(res);
    }

    public String list() {
        List<Employee> list = jdbcTemplate.query("select * from employee", new Object[]{}, new BeanPropertyRowMapper<>(Employee.class));
        return JSON.toJSONString(list);
    }

    public String update(Employee emp) {
        int res = jdbcTemplate.update("update employee set name = ? where id = ?", emp.name, emp.id);
        return String.valueOf(res);
    }

    public String del(Employee emp) {
        int res = jdbcTemplate.update("delete from employee where id = ?", emp.id);
        return String.valueOf(res);
    }
}
