package app.smile.smilepathway.model;

import java.util.List;

public class SmilePlanModel {

    /**
     * status : true
     * error : {"error":0,"errormsg":""}
     * result : [{"id":"3","pms_patient_id":"6","pms_treatment_id":"8","tp_heading":"Treatment plan-8","tp_date":"2020-09-22","is_selected":"0","is_generated":"1"},{"id":"4","pms_patient_id":"6","pms_treatment_id":"18","tp_heading":"Treatment plan-18","tp_date":"2020-09-22","is_selected":"1","is_generated":"1"},{"id":"5","pms_patient_id":"6","pms_treatment_id":"20","tp_heading":"Treatment plan-20","tp_date":"2020-09-22","is_selected":"0","is_generated":"1"}]
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

        private String error;
        private String errormsg;

        public String  getError() {
            return error;
        }

        public void setError(String error) {
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
         * id : 3
         * pms_patient_id : 6
         * pms_treatment_id : 8
         * tp_heading : Treatment plan-8
         * tp_date : 2020-09-22
         * is_selected : 0
         * is_generated : 1
         */

        private String id;
        private String pms_patient_id;
        private String pms_treatment_id;
        private String tp_heading;
        private String tp_date;
        private String is_selected;
        private String is_generated;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPms_patient_id() {
            return pms_patient_id;
        }

        public void setPms_patient_id(String pms_patient_id) {
            this.pms_patient_id = pms_patient_id;
        }

        public String getPms_treatment_id() {
            return pms_treatment_id;
        }

        public void setPms_treatment_id(String pms_treatment_id) {
            this.pms_treatment_id = pms_treatment_id;
        }

        public String getTp_heading() {
            return tp_heading;
        }

        public void setTp_heading(String tp_heading) {
            this.tp_heading = tp_heading;
        }

        public String getTp_date() {
            return tp_date;
        }

        public void setTp_date(String tp_date) {
            this.tp_date = tp_date;
        }

        public String getIs_selected() {
            return is_selected;
        }

        public void setIs_selected(String is_selected) {
            this.is_selected = is_selected;
        }

        public String getIs_generated() {
            return is_generated;
        }

        public void setIs_generated(String is_generated) {
            this.is_generated = is_generated;
        }
    }
}
