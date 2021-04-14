package app.smile.smilepathway.model;

import java.util.List;

public class NotificationListModel {

    /**
     * status : true
     * error : {"error":0,"errormsg":""}
     * result : [{"id":"32","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 10-11-2020 because test","created_at":"2020-11-09 16:18:35","updated_at":"2020-11-09 16:18:35","is_active":"1","is_deleted":"0"},{"id":"31","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 08-11-2020 because test message","created_at":"2020-11-07 17:23:48","updated_at":"2020-11-07 17:23:48","is_active":"1","is_deleted":"0"},{"id":"30","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 08-11-2020 because asdfs dsadasdf","created_at":"2020-11-07 16:14:56","updated_at":"2020-11-07 16:14:56","is_active":"1","is_deleted":"0"},{"id":"29","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 08-11-2020 because dgsdf","created_at":"2020-11-07 16:13:47","updated_at":"2020-11-07 16:13:47","is_active":"1","is_deleted":"0"},{"id":"28","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 08-11-2020","created_at":"2020-11-07 16:00:32","updated_at":"2020-11-07 16:00:32","is_active":"1","is_deleted":"0"},{"id":"27","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 08-11-2020","created_at":"2020-11-07 15:47:28","updated_at":"2020-11-07 15:47:28","is_active":"1","is_deleted":"0"},{"id":"26","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 05-11-2020","created_at":"2020-11-04 20:03:42","updated_at":"2020-11-04 20:03:42","is_active":"1","is_deleted":"0"},{"id":"25","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 05-11-2020 because message 123","created_at":"2020-11-04 20:01:33","updated_at":"2020-11-04 20:01:33","is_active":"1","is_deleted":"0"},{"id":"24","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 05-11-2020 because i am busy on that time ","created_at":"2020-11-04 16:50:31","updated_at":"2020-11-04 16:50:31","is_active":"1","is_deleted":"0"},{"id":"23","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 05-11-2020because this is test","created_at":"2020-11-04 16:45:35","updated_at":"2020-11-04 16:45:35","is_active":"1","is_deleted":"0"},{"id":"22","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 01-11-2020","created_at":"2020-10-31 15:01:34","updated_at":"2020-10-31 15:01:34","is_active":"1","is_deleted":"0"},{"id":"21","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 30-10-2020","created_at":"2020-10-29 17:21:23","updated_at":"2020-10-29 17:21:23","is_active":"1","is_deleted":"0"},{"id":"20","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 30-10-2020","created_at":"2020-10-29 16:45:18","updated_at":"2020-10-29 16:45:18","is_active":"1","is_deleted":"0"},{"id":"19","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 30-10-2020","created_at":"2020-10-29 16:33:58","updated_at":"2020-10-29 16:33:58","is_active":"1","is_deleted":"0"},{"id":"17","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 30-10-2020","created_at":"2020-10-29 15:46:59","updated_at":"2020-10-29 15:46:59","is_active":"1","is_deleted":"0"},{"id":"16","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 30-10-2020","created_at":"2020-10-29 15:46:55","updated_at":"2020-10-29 15:46:55","is_active":"1","is_deleted":"0"},{"id":"15","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"You have new appointment scheduled with Smile Loft on 30-10-2020","created_at":"2020-10-29 15:33:40","updated_at":"2020-10-29 15:33:40","is_active":"1","is_deleted":"0"},{"id":"14","user_id":"9","patient_id":"185","module":"treatment","notification_id":"12","message":"Sorry ! I have reject your appointment scheduled on 29-10-2020","created_at":"2020-10-28 21:21:16","updated_at":"2020-10-28 21:21:16","is_active":"1","is_deleted":"0"}]
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
         * id : 32
         * user_id : 9
         * patient_id : 185
         * module : treatment
         * notification_id : 12
         * message : Sorry ! I have reject your appointment scheduled on 10-11-2020 because test
         * created_at : 2020-11-09 16:18:35
         * updated_at : 2020-11-09 16:18:35
         * is_active : 1
         * is_deleted : 0
         */

        private String id;
        private String user_id;
        private String patient_id;
        private String module;
        private String notification_id;
        private String message;
        private String created_at;
        private String updated_at;
        private String is_active;
        private String is_deleted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPatient_id() {
            return patient_id;
        }

        public void setPatient_id(String patient_id) {
            this.patient_id = patient_id;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getNotification_id() {
            return notification_id;
        }

        public void setNotification_id(String notification_id) {
            this.notification_id = notification_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(String is_deleted) {
            this.is_deleted = is_deleted;
        }
    }
}
