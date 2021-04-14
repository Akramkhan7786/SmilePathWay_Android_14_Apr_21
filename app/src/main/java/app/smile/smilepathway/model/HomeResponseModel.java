package app.smile.smilepathway.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponseModel {

    /**
     * status : true
     * error : {}
     * result : {"display_check":true,"selected_treatment_plan":[{"id":"4","pms_patient_id":"6","pms_treatment_id":"18","tp_heading":"Treatment plan-18","tp_date":"Sep 22, 2020","is_selected":"1","is_generated":"1"}],"upcomming_appointment":{"id":"185","appointments_arr":[{"id":"106","pms_patient_id":"6","pms_treatment_id":"18","appointment_number":"3","appointment_time":"30","appointment_source":null,"pms_schedule_date":null,"pms_start_time":null,"pms_stop_time":null,"patient_schedule_date":"2020-12-12","patient_start_time":"11:00:00","patient_stop_time":"11:30:00","schedule_date":"Dec 12, 2020","start_time":"11:00:00","stop_time":"11:30:00","appointment_status":"2","procedures":[{"id":"134","pms_procedure_id":"80","order_number":"1","tooth_number":"0","cdt_code":"D4240","proc_time":"30","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"325","short_description":"Gingival flap proc w/ planin","office_fee":"9.00","insurance_coverage":"0","procedure_payable_amount":"9.00","cdt_category":"Periodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}],"appointment_amount":9}]},"invoice_amount":{"gross_amount":"500"},"userdata":{"id":"185","username":"76630","firstname":"ANIL","lastname":"KUMAR","email":"sha.rma.mayur84@gmail.com","gender":"M","dob":"May 05, 2019","profile_image":"","mobile_number":"5656565656","age":1},"setting":{"2FA":"false","notification_enabled":"true"}}
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
         * display_check : true
         * selected_treatment_plan : [{"id":"4","pms_patient_id":"6","pms_treatment_id":"18","tp_heading":"Treatment plan-18","tp_date":"Sep 22, 2020","is_selected":"1","is_generated":"1"}]
         * upcomming_appointment : {"id":"185","appointments_arr":[{"id":"106","pms_patient_id":"6","pms_treatment_id":"18","appointment_number":"3","appointment_time":"30","appointment_source":null,"pms_schedule_date":null,"pms_start_time":null,"pms_stop_time":null,"patient_schedule_date":"2020-12-12","patient_start_time":"11:00:00","patient_stop_time":"11:30:00","schedule_date":"Dec 12, 2020","start_time":"11:00:00","stop_time":"11:30:00","appointment_status":"2","procedures":[{"id":"134","pms_procedure_id":"80","order_number":"1","tooth_number":"0","cdt_code":"D4240","proc_time":"30","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"325","short_description":"Gingival flap proc w/ planin","office_fee":"9.00","insurance_coverage":"0","procedure_payable_amount":"9.00","cdt_category":"Periodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}],"appointment_amount":9}]}
         * invoice_amount : {"gross_amount":"500"}
         * userdata : {"id":"185","username":"76630","firstname":"ANIL","lastname":"KUMAR","email":"sha.rma.mayur84@gmail.com","gender":"M","dob":"May 05, 2019","profile_image":"","mobile_number":"5656565656","age":1}
         * setting : {"2FA":"false","notification_enabled":"true"}
         */

        private boolean display_check;
        private UserDataModel userdata;
        private SettingBean setting;
        private UpcommingAppointmentBean upcomming_appointment;
        private InvoiceAmountBean invoice_amount;
        private List<SelectedTreatmentPlanBean> selected_treatment_plan;

        public boolean isDisplay_check() {
            return display_check;
        }

        public void setDisplay_check(boolean display_check) {
            this.display_check = display_check;
        }

        public UpcommingAppointmentBean getUpcomming_appointment() {
            return upcomming_appointment;
        }

        public void setUpcomming_appointment(UpcommingAppointmentBean upcomming_appointment) {
            this.upcomming_appointment = upcomming_appointment;
        }

        public InvoiceAmountBean getInvoice_amount() {
            return invoice_amount;
        }

        public void setInvoice_amount(InvoiceAmountBean invoice_amount) {
            this.invoice_amount = invoice_amount;
        }

        public UserDataModel getUserdata() {
            return userdata;
        }

        public void setUserdata(UserDataModel userdata) {
            this.userdata = userdata;
        }

        public SettingBean getSetting() {
            return setting;
        }

        public void setSetting(SettingBean setting) {
            this.setting = setting;
        }

        public List<SelectedTreatmentPlanBean> getSelected_treatment_plan() {
            return selected_treatment_plan;
        }

        public void setSelected_treatment_plan(List<SelectedTreatmentPlanBean> selected_treatment_plan) {
            this.selected_treatment_plan = selected_treatment_plan;
        }

        public static class UpcommingAppointmentBean {
            /**
             * id : 185
             * appointments_arr : [{"id":"106","pms_patient_id":"6","pms_treatment_id":"18","appointment_number":"3","appointment_time":"30","appointment_source":null,"pms_schedule_date":null,"pms_start_time":null,"pms_stop_time":null,"patient_schedule_date":"2020-12-12","patient_start_time":"11:00:00","patient_stop_time":"11:30:00","schedule_date":"Dec 12, 2020","start_time":"11:00:00","stop_time":"11:30:00","appointment_status":"2","procedures":[{"id":"134","pms_procedure_id":"80","order_number":"1","tooth_number":"0","cdt_code":"D4240","proc_time":"30","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"325","short_description":"Gingival flap proc w/ planin","office_fee":"9.00","insurance_coverage":"0","procedure_payable_amount":"9.00","cdt_category":"Periodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}],"appointment_amount":9}]
             */

            private String id;
            private List<AppointmentsArrBean> appointments_arr;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<AppointmentsArrBean> getAppointments_arr() {
                return appointments_arr;
            }

            public void setAppointments_arr(List<AppointmentsArrBean> appointments_arr) {
                this.appointments_arr = appointments_arr;
            }

        }

        public static class InvoiceAmountBean {
            /**
             * gross_amount : 500
             */

            private String gross_amount;

            public String getGross_amount() {
                return gross_amount;
            }

            public void setGross_amount(String gross_amount) {
                this.gross_amount = gross_amount;
            }
        }

        public static class SettingBean {
            /**
             * 2FA : false
             * notification_enabled : true
             */

            @SerializedName("2FA")
            private String _$2FA;
            private String notification_enabled;

            public String get_$2FA() {
                return _$2FA;
            }

            public void set_$2FA(String _$2FA) {
                this._$2FA = _$2FA;
            }

            public String getNotification_enabled() {
                return notification_enabled;
            }

            public void setNotification_enabled(String notification_enabled) {
                this.notification_enabled = notification_enabled;
            }
        }

        public static class SelectedTreatmentPlanBean {
            /**
             * id : 4
             * pms_patient_id : 6
             * pms_treatment_id : 18
             * tp_heading : Treatment plan-18
             * tp_date : Sep 22, 2020
             * is_selected : 1
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
}
