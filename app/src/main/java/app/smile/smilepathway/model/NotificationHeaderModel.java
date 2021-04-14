package app.smile.smilepathway.model;

import java.util.List;

public class NotificationHeaderModel {

    /**
     * status : true
     * error : {"error":0,"errormsg":""}
     * result : [{"id":"10","notification_type":"PtN1","group":"patient","parent_id":"0","counter":0},{"id":"11","notification_type":"PtN2","group":"patient","parent_id":"0","counter":0},{"id":"12","notification_type":"PtN3","group":"patient","parent_id":"0","counter":18},{"id":"13","notification_type":"PtN4","group":"patient","parent_id":"0","counter":0}]
     * code : 200
     */

    private boolean status;
    private ErrorBean error;
    private int code;
    private List<ResultBean> result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ErrorBean {
        /**
         * error : 0
         * errormsg :
         */

        private int error;
        private String errormsg;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getErrormsg() {
            return errormsg;
        }

        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }
    }

    public static class ResultBean {
        /**
         * id : 10
         * notification_type : PtN1
         * group : patient
         * parent_id : 0
         * counter : 0
         */

        private String id;
        private String notification_type;
        private String group;
        private String parent_id;
        private String counter;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNotification_type() {
            return notification_type;
        }

        public void setNotification_type(String notification_type) {
            this.notification_type = notification_type;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getCounter() {
            return counter;
        }

        public void setCounter(String counter) {
            this.counter = counter;
        }
    }
}
