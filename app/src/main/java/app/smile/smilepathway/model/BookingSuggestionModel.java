package app.smile.smilepathway.model;

import java.util.List;

public class BookingSuggestionModel {

    /**
     * status : true
     * error : {}
     * result : {"id":"15","suggestion":["20-11-2020 10:00 AM - 10:40 AM","20-11-2020 10:40 AM - 11:20 AM","20-11-2020 11:20 AM - 12:00 PM"],"estimated_time":"40","pms_patient_id":"74","appointment_number":"1","pms_treatment_id":"101"}
     * code : 200
     */

    private boolean status;
    private ErrorBean error;
    private ResultBean result;
    private int code;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ErrorBean {
    }

    public static class ResultBean {
        /**
         * id : 15
         * suggestion : ["20-11-2020 10:00 AM - 10:40 AM","20-11-2020 10:40 AM - 11:20 AM","20-11-2020 11:20 AM - 12:00 PM"]
         * estimated_time : 40
         * pms_patient_id : 74
         * appointment_number : 1
         * pms_treatment_id : 101
         */

        private String id;
        private String estimated_time;
        private String pms_patient_id;
        private String appointment_number;
        private String pms_treatment_id;
        private List<String> suggestion;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEstimated_time() {
            return estimated_time;
        }

        public void setEstimated_time(String estimated_time) {
            this.estimated_time = estimated_time;
        }

        public String getPms_patient_id() {
            return pms_patient_id;
        }

        public void setPms_patient_id(String pms_patient_id) {
            this.pms_patient_id = pms_patient_id;
        }

        public String getAppointment_number() {
            return appointment_number;
        }

        public void setAppointment_number(String appointment_number) {
            this.appointment_number = appointment_number;
        }

        public String getPms_treatment_id() {
            return pms_treatment_id;
        }

        public void setPms_treatment_id(String pms_treatment_id) {
            this.pms_treatment_id = pms_treatment_id;
        }

        public List<String> getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(List<String> suggestion) {
            this.suggestion = suggestion;
        }
    }
}
