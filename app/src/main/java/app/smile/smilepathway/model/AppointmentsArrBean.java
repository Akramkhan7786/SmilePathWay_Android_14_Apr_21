package app.smile.smilepathway.model;

import java.util.List;

public class AppointmentsArrBean {
    /**
     * id : 105
     * pms_patient_id : 6
     * pms_treatment_id : 18
     * appointment_number : 1
     * appointment_time : 60
     * appointment_status : 0
     * schedule_date : null
     * start_time : null
     * stop_time : null
     * is_locked : 1
     * procedures : [{"id":"133","pms_procedure_id":"86","order_number":"1","tooth_number":"4","cdt_code":"D3240","proc_time":"60","is_ppd":"0","ppd_flag":null,"prefix_suffix":null,"cdt_code_id":"268","short_description":"Pulpal therapy posterior pri","office_fee":"59.00","insurance_coverage":"0","procedure_payable_amount":"59.00","cdt_category":"Endodontics","insurance_coverage_amount":0,"practice_default_procedure_fee":"O","procedure_insurance_fee":0,"display_low_coverage_info":0,"available_balance":0}]
     * appointment_amount : 59
     */

    private String id;
    private String pms_patient_id;
    private String pms_treatment_id;
    private String appointment_number;
    private String appointment_time;
    private String appointment_source;
    private String pms_schedule_date;
    private String pms_start_time;
    private String pms_stop_time;
    private String patient_schedule_date;
    private String patient_start_time;
    private String patient_stop_time;
    private String appointment_status;
    private String schedule_date;
    private String start_time;
    private String stop_time;
    private String is_locked;
    private String appointment_amount;
    private String schedule_date_time;

    public String getSchedule_date_time() {
        return schedule_date_time;
    }

    public void setSchedule_date_time(String schedule_date_time) {
        this.schedule_date_time = schedule_date_time;
    }

    private List<ProceduresBean> procedures;

    public String getAppointment_source() {
        return appointment_source;
    }

    public void setAppointment_source(String appointment_source) {
        this.appointment_source = appointment_source;
    }

    public String getPms_schedule_date() {
        return pms_schedule_date;
    }

    public void setPms_schedule_date(String pms_schedule_date) {
        this.pms_schedule_date = pms_schedule_date;
    }

    public String getPms_start_time() {
        return pms_start_time;
    }

    public void setPms_start_time(String pms_start_time) {
        this.pms_start_time = pms_start_time;
    }

    public String getPms_stop_time() {
        return pms_stop_time;
    }

    public void setPms_stop_time(String pms_stop_time) {
        this.pms_stop_time = pms_stop_time;
    }

    public String getPatient_schedule_date() {
        return patient_schedule_date;
    }

    public void setPatient_schedule_date(String patient_schedule_date) {
        this.patient_schedule_date = patient_schedule_date;
    }

    public String getPatient_start_time() {
        return patient_start_time;
    }

    public void setPatient_start_time(String patient_start_time) {
        this.patient_start_time = patient_start_time;
    }

    public String getPatient_stop_time() {
        return patient_stop_time;
    }

    public void setPatient_stop_time(String patient_stop_time) {
        this.patient_stop_time = patient_stop_time;
    }

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

    public String getAppointment_number() {
        return appointment_number;
    }

    public void setAppointment_number(String appointment_number) {
        this.appointment_number = appointment_number;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getAppointment_status() {
        return appointment_status;
    }

    public void setAppointment_status(String appointment_status) {
        this.appointment_status = appointment_status;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    public String getAppointment_amount() {
        return appointment_amount;
    }

    public void setAppointment_amount(String appointment_amount) {
        this.appointment_amount = appointment_amount;
    }

    public List<ProceduresBean> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<ProceduresBean> procedures) {
        this.procedures = procedures;
    }
}
