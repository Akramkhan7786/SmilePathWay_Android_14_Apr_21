package app.smile.smilepathway.model;

import java.util.List;

public class InvoiceModel {

    /**
     * status : true
     * error : {}
     * result : [{"id":"30","invoice_number":"1015","due_date":"December 20, 2020","status":"Paid","payment_mode":"Cash","payment_mode_description":"","payment_date":"December 10, 2020","gross_amount":"505"},{"id":"29","invoice_number":"1014","due_date":"December 19, 2020","status":"Paid","payment_mode":"Cheque","payment_mode_description":"","payment_date":"December 09, 2020","gross_amount":"100"}]
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
    }

    public static class ResultBean {
        /**
         * id : 30
         * invoice_number : 1015
         * due_date : December 20, 2020
         * status : Paid
         * payment_mode : Cash
         * payment_mode_description :
         * payment_date : December 10, 2020
         * gross_amount : 505
         */

        private String id;
        private String invoice_number;
        private String due_date;
        private String status;
        private String payment_mode;
        private String payment_mode_description;
        private String payment_date;
        private String invoice_create_date;

        public String getInvoice_create_date() {
            return invoice_create_date;
        }

        public void setInvoice_create_date(String invoice_create_date) {
            this.invoice_create_date = invoice_create_date;
        }

        private String gross_amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInvoice_number() {
            return invoice_number;
        }

        public void setInvoice_number(String invoice_number) {
            this.invoice_number = invoice_number;
        }

        public String getDue_date() {
            return due_date;
        }

        public void setDue_date(String due_date) {
            this.due_date = due_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getPayment_mode_description() {
            return payment_mode_description;
        }

        public void setPayment_mode_description(String payment_mode_description) {
            this.payment_mode_description = payment_mode_description;
        }

        public String getPayment_date() {
            return payment_date;
        }

        public void setPayment_date(String payment_date) {
            this.payment_date = payment_date;
        }

        public String getGross_amount() {
            return gross_amount;
        }

        public void setGross_amount(String gross_amount) {
            this.gross_amount = gross_amount;
        }
    }
}
