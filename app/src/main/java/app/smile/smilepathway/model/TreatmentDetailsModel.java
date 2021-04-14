package app.smile.smilepathway.model;

import java.util.List;

public class TreatmentDetailsModel {

    /**
     * status : true
     * error : {}
     * result : {"appointments_arr":[{"id":"105","pms_patient_id":"6","pms_treatment_id":"18","appointment_number":"1","appointment_time":"60","appointment_status":0,"schedule_date":null,"start_time":null,"stop_time":null,"is_locked":"1","procedures":[{"id":"133","pms_procedure_id":"86","order_number":"1","tooth_number":"4","cdt_code":"D3240","proc_time":"60","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"268","short_description":"Pulpal therapy posterior pri","office_fee":"59.00","insurance_coverage":"0","procedure_payable_amount":"59.00","cdt_category":"Endodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}],"appointment_amount":59},{"id":"106","pms_patient_id":"6","pms_treatment_id":"18","appointment_number":"3","appointment_time":"30","appointment_status":0,"schedule_date":null,"start_time":null,"stop_time":null,"is_locked":"1","procedures":[{"id":"134","pms_procedure_id":"80","order_number":"1","tooth_number":"0","cdt_code":"D4240","proc_time":"30","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"325","short_description":"Gingival flap proc w/ planin","office_fee":"9.00","insurance_coverage":"0","procedure_payable_amount":"9.00","cdt_category":"Periodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}],"appointment_amount":9}],"id":"185","default_interval":"10","previous_count":1,"scheduled_count":0,"unscheduled_count":2}
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
         * appointments_arr : [{"id":"105","pms_patient_id":"6","pms_treatment_id":"18","appointment_number":"1","appointment_time":"60","appointment_status":0,"schedule_date":null,"start_time":null,"stop_time":null,"is_locked":"1","procedures":[{"id":"133","pms_procedure_id":"86","order_number":"1","tooth_number":"4","cdt_code":"D3240","proc_time":"60","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"268","short_description":"Pulpal therapy posterior pri","office_fee":"59.00","insurance_coverage":"0","procedure_payable_amount":"59.00","cdt_category":"Endodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}],"appointment_amount":59},{"id":"106","pms_patient_id":"6","pms_treatment_id":"18","appointment_number":"3","appointment_time":"30","appointment_status":0,"schedule_date":null,"start_time":null,"stop_time":null,"is_locked":"1","procedures":[{"id":"134","pms_procedure_id":"80","order_number":"1","tooth_number":"0","cdt_code":"D4240","proc_time":"30","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"325","short_description":"Gingival flap proc w/ planin","office_fee":"9.00","insurance_coverage":"0","procedure_payable_amount":"9.00","cdt_category":"Periodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}],"appointment_amount":9}]
         * id : 185
         * default_interval : 10
         * previous_count : 1
         * scheduled_count : 0
         * unscheduled_count : 2
         */

        private String id;
        private String default_interval;
        private String previous_count;
        private String scheduled_count;
        private String unscheduled_count;
        private List<AppointmentsArrBean> appointments_arr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDefault_interval() {
            return default_interval;
        }

        public void setDefault_interval(String default_interval) {
            this.default_interval = default_interval;
        }

        public String getPrevious_count() {
            return previous_count;
        }

        public void setPrevious_count(String previous_count) {
            this.previous_count = previous_count;
        }

        public String getScheduled_count() {
            return scheduled_count;
        }

        public void setScheduled_count(String scheduled_count) {
            this.scheduled_count = scheduled_count;
        }

        public String getUnscheduled_count() {
            return unscheduled_count;
        }

        public void setUnscheduled_count(String unscheduled_count) {
            this.unscheduled_count = unscheduled_count;
        }

        public List<AppointmentsArrBean> getAppointments_arr() {
            return appointments_arr;
        }

        public void setAppointments_arr(List<AppointmentsArrBean> appointments_arr) {
            this.appointments_arr = appointments_arr;
        }
    }
}
