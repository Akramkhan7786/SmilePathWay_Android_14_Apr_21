package app.smile.smilepathway.model;

public class ClientSecertModel {

    /**
     * status : true
     * error : {}
     * result : {"invoice_id":"31","client_secret":"pi_1I1VhxE0sPrZ7AoYGGKi6pA4_secret_438nRyopXUrEs6JM7jIMG2ogx"}
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
         * invoice_id : 31
         * client_secret : pi_1I1VhxE0sPrZ7AoYGGKi6pA4_secret_438nRyopXUrEs6JM7jIMG2ogx
         */

        private String invoice_id;
        private String client_secret;
        private String customerid;
        private String ephemeralKeySecret;
        private String payment_intent_id;

        public String getCustomerid() {
            return customerid;
        }

        public void setCustomerid(String customerid) {
            this.customerid = customerid;
        }

        public String getEphemeralKeySecret() {
            return ephemeralKeySecret;
        }

        public void setEphemeralKeySecret(String ephemeralKeySecret) {
            this.ephemeralKeySecret = ephemeralKeySecret;
        }

        public String getPayment_intent_id() {
            return payment_intent_id;
        }

        public void setPayment_intent_id(String payment_intent_id) {
            this.payment_intent_id = payment_intent_id;
        }

        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }

        public String getClient_secret() {
            return client_secret;
        }

        public void setClient_secret(String client_secret) {
            this.client_secret = client_secret;
        }
    }
}
