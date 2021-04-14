package app.smile.smilepathway.model;

public class SmileVerifyModel {

    /**
     * status : true
     * error : {}
     * result : {"id":"16","pms_patient_id":"6","annual":null,"amount_used":null,"amount_remaining":null,"ortho_lifetime":null,"ortho_used":null,"ortho_remaining":null,"diagnostic":"0%","preventive":"0%","restroative":"0%","restroative_direct":"0%","restroative_indirect":"0%","perio":"0%","oral_surgery":"0%","prosth_fixed":"0%","prosth_removable":"0%","ortho":"0%","crowns":"0%","endo":"0%"}
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
         * id : 16
         * pms_patient_id : 6
         * annual : null
         * amount_used : null
         * amount_remaining : null
         * ortho_lifetime : null
         * ortho_used : null
         * ortho_remaining : null
         * diagnostic : 0%
         * preventive : 0%
         * restroative : 0%
         * restroative_direct : 0%
         * restroative_indirect : 0%
         * perio : 0%
         * oral_surgery : 0%
         * prosth_fixed : 0%
         * prosth_removable : 0%
         * ortho : 0%
         * crowns : 0%
         * endo : 0%
         * status : error
         * message : No Real Time Eligibility Available
         */

        private String id;
        private String pms_patient_id;
        private String annual;
        private String amount_used;
        private String amount_remaining;
        private String ortho_lifetime;
        private String ortho_used;
        private String ortho_remaining;
        private String diagnostic;
        private String preventive;
        private String restroative;
        private String restroative_direct;
        private String restroative_indirect;
        private String perio;
        private String oral_surgery;
        private String prosth_fixed;
        private String prosth_removable;
        private String ortho;
        private String crowns;
        private String endo;
        private String status;
        private String message;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
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

        public String getAnnual() {
            return annual;
        }

        public void setAnnual(String annual) {
            this.annual = annual;
        }

        public String getAmount_used() {
            return amount_used;
        }

        public void setAmount_used(String amount_used) {
            this.amount_used = amount_used;
        }

        public String getAmount_remaining() {
            return amount_remaining;
        }

        public void setAmount_remaining(String amount_remaining) {
            this.amount_remaining = amount_remaining;
        }

        public String getOrtho_lifetime() {
            return ortho_lifetime;
        }

        public void setOrtho_lifetime(String ortho_lifetime) {
            this.ortho_lifetime = ortho_lifetime;
        }

        public String getOrtho_used() {
            return ortho_used;
        }

        public void setOrtho_used(String ortho_used) {
            this.ortho_used = ortho_used;
        }

        public String getOrtho_remaining() {
            return ortho_remaining;
        }

        public void setOrtho_remaining(String ortho_remaining) {
            this.ortho_remaining = ortho_remaining;
        }

        public String getDiagnostic() {
            return diagnostic;
        }

        public void setDiagnostic(String diagnostic) {
            this.diagnostic = diagnostic;
        }

        public String getPreventive() {
            return preventive;
        }

        public void setPreventive(String preventive) {
            this.preventive = preventive;
        }

        public String getRestroative() {
            return restroative;
        }

        public void setRestroative(String restroative) {
            this.restroative = restroative;
        }

        public String getRestroative_direct() {
            return restroative_direct;
        }

        public void setRestroative_direct(String restroative_direct) {
            this.restroative_direct = restroative_direct;
        }

        public String getRestroative_indirect() {
            return restroative_indirect;
        }

        public void setRestroative_indirect(String restroative_indirect) {
            this.restroative_indirect = restroative_indirect;
        }

        public String getPerio() {
            return perio;
        }

        public void setPerio(String perio) {
            this.perio = perio;
        }

        public String getOral_surgery() {
            return oral_surgery;
        }

        public void setOral_surgery(String oral_surgery) {
            this.oral_surgery = oral_surgery;
        }

        public String getProsth_fixed() {
            return prosth_fixed;
        }

        public void setProsth_fixed(String prosth_fixed) {
            this.prosth_fixed = prosth_fixed;
        }

        public String getProsth_removable() {
            return prosth_removable;
        }

        public void setProsth_removable(String prosth_removable) {
            this.prosth_removable = prosth_removable;
        }

        public String getOrtho() {
            return ortho;
        }

        public void setOrtho(String ortho) {
            this.ortho = ortho;
        }

        public String getCrowns() {
            return crowns;
        }

        public void setCrowns(String crowns) {
            this.crowns = crowns;
        }

        public String getEndo() {
            return endo;
        }

        public void setEndo(String endo) {
            this.endo = endo;
        }
    }
}
