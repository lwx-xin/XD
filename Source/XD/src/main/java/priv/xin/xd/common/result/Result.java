package priv.xin.xd.common.result;

import priv.xin.xd.core.entity.Code;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/5/31 17:24
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -6499388341207392959L;

    private boolean status;

    private List<Map<String, String>> messages;

    private Map<String, List<Code>> codes;

    private Map<String, Object> datas;

    public Result(boolean status) {
        this.status = status;
    }

    public Result clearData() {
        if(codes!=null){
            codes.clear();
        }
        if(datas!=null){
            datas.clear();
        }
        return this;
    }

    /**
     * 添加消息
     *
     * @param messageLevel
     * @param message
     * @return
     */
    public Result message(String messageLevel, Enum<? extends Enum> message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        String code = "", msg = "";
        if (message instanceof priv.xin.xd.check.system.Message) {
            priv.xin.xd.check.system.Message m = (priv.xin.xd.check.system.Message) message;
            code = m.getCode();
            msg = m.getMessage();
        }

        HashMap hashMap = new HashMap();
        hashMap.put("code", code);
        hashMap.put("level", messageLevel);
        hashMap.put("msg", msg);
        this.messages.add(hashMap);
        return this;
    }

    /**
     * 添加消息
     *
     * @param messageLevel 消息级别 MessageLevel
     * @param message
     * @return
     */
    public Result message(String messageLevel, String message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }

        HashMap hashMap = new HashMap();
        hashMap.put("code", "S0000");
        hashMap.put("level", messageLevel);
        hashMap.put("msg", message);
        this.messages.add(hashMap);
        return this;
    }

    /**
     * 添加code列表
     *
     * @param codes
     * @return
     */
    public Result codes(Map<String, List<Code>> codes) {
        if (this.codes == null) {
            this.codes = new HashMap<>();
        }
        this.codes.putAll(codes);
        return this;
    }

    /**
     * 添加code列表
     *
     * @param code
     * @return
     */
    public Result code(List<Code> code) {
        if (this.codes == null) {
            this.codes = new HashMap<>();
        }
        this.codes.put(code.get(0).getCodeGroup(), code);
        return this;
    }

    /**
     * 添加返回结果
     *
     * @param datas
     * @return
     */
    public Result datas(Map<String, Object> datas) {
        if (this.datas == null) {
            this.datas = new HashMap<>();
        }
        this.datas.putAll(datas);
        return this;
    }

    /**
     * 添加返回结果
     *
     * @param data
     * @return
     */
    public Result data(String key, Object data) {
        if (this.datas == null) {
            this.datas = new HashMap<>();
        }
        this.datas.put(key, data);
        return this;
    }

    public Result status(boolean status) {
        this.status = status;
        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(List<Map<String, String>> messages) {
        this.messages = messages;
    }

    public Map<String, List<Code>> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, List<Code>> codes) {
        this.codes = codes;
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public Object getData(String key) {
        return datas.get(key);
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", messages=" + messages +
                ", codes=" + codes +
                ", datas=" + datas +
                '}';
    }
}
