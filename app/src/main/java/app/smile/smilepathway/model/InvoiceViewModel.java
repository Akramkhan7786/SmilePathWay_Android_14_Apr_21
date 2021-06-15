package app.smile.smilepathway.model;

import java.util.List;

public class InvoiceViewModel {

    /**
     * status : true
     * error : {}
     * result : {"practice_details":{"logo":"https://practices-staging.smilepathway.com/assets/images/practice_logo/311616664242.png","practice_name":"Smile Loft","practice_address":"SMILEPATHWAY, TRUMP TOWER","practice_state":"NY","practice_city":"New York","practice_pincode":"02163","practice_stripe_id":"acct_1IDRWOLwYF9McPCL"},"patient_details":{"name":"ONE ANA","address":"None","city":"None","state":"None","zipcode":"None"},"invoice":{"id":"48","invoice_number":"1046","due_date":"Mar 27, 2021","status":"Presented","payment_mode":"","payment_mode_description":null,"payment_date":"N/A","gross_amount":1500},"invoice_details":[{"description":"test","amount":1000,"tax":50,"total_amount":1500}]}
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
         * practice_details : {"logo":"https://practices-staging.smilepathway.com/assets/images/practice_logo/311616664242.png","practice_name":"Smile Loft","practice_address":"SMILEPATHWAY, TRUMP TOWER","practice_state":"NY","practice_city":"New York","practice_pincode":"02163","practice_stripe_id":"acct_1IDRWOLwYF9McPCL"}
         * patient_details : {"name":"ONE ANA","address":"None","city":"None","state":"None","zipcode":"None"}
         * invoice : {"id":"48","invoice_number":"1046","due_date":"Mar 27, 2021","status":"Presented","payment_mode":"","payment_mode_description":null,"payment_date":"N/A","gross_amount":1500}
         * invoice_details : [{"description":"test","amount":1000,"tax":50,"total_amount":1500}]
         */

        private PracticeDetailsBean practice_details;
        private PatientDetailsBean patient_details;
        private InvoiceBean invoice;
        private List<InvoiceDetailsBean> invoice_details;

        public PracticeDetailsBean getPractice_details() {
            return practice_details;
        }

        public void setPractice_details(PracticeDetailsBean practice_details) {
            this.practice_details = practice_details;
        }

        public PatientDetailsBean getPatient_details() {
            return patient_details;
        }

        public void setPatient_details(PatientDetailsBean patient_details) {
            this.patient_details = patient_details;
        }

        public InvoiceBean getInvoice() {
            return invoice;
        }

        public void setInvoice(InvoiceBean invoice) {
            this.invoice = invoice;
        }

        public List<InvoiceDetailsBean> getInvoice_details() {
            return invoice_details;
        }

        public void setInvoice_details(List<InvoiceDetailsBean> invoice_details) {
            this.invoice_details = invoice_details;
        }

        public static class PracticeDetailsBean {
            /**
             * logo : https://practices-staging.smilepathway.com/assets/images/practice_logo/311616664242.png
             * practice_name : Smile Loft
             * practice_address : SMILEPATHWAY, TRUMP TOWER
             * practice_state : NY
             * practice_city : New York
             * practice_pincode : 02163
             * practice_stripe_id : acct_1IDRWOLwYF9McPCL
             */

            private String logo;
            private String practice_name;
            private String practice_address;
            private String practice_state;
            private String practice_city;
            private String practice_pincode;
            private String practice_stripe_id;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getPractice_name() {
                return practice_name;
            }

            public void setPractice_name(String practice_name) {
                this.practice_name = practice_name;
            }

            public String getPractice_address() {
                return practice_address;
            }

            public void setPractice_address(String practice_address) {
                this.practice_address = practice_address;
            }

            public String getPractice_state() {
                return practice_state;
            }

            public void setPractice_state(String practice_state) {
                this.practice_state = practice_state;
            }

            public String getPractice_city() {
                return practice_city;
            }

            public void setPractice_city(String practice_city) {
                this.practice_city = practice_city;
            }

            public String getPractice_pincode() {
                return practice_pincode;
            }

            public void setPractice_pincode(String practice_pincode) {
                this.practice_pincode = practice_pincode;
            }

            public String getPractice_stripe_id() {
                return practice_stripe_id;
            }

            public void setPractice_stripe_id(String practice_stripe_id) {
                this.practice_stripe_id = practice_stripe_id;
            }
        }

        public static class PatientDetailsBean {
            /**
             * name : ONE ANA
             * address : None
             * city : None
             * state : None
             * zipcode : None
             */

            private String name;
            private String address;
            private String city;
            private String state;
            private String zipcode;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }
        }

        public static class InvoiceBean {
            /**
             * id : 48
             * invoice_number : 1046
             * due_date : Mar 27, 2021
             * status : Presented
             * payment_mode :
             * payment_mode_description : null
             * payment_date : N/A
             * gross_amount : 1500
             */

            private String id;
            private String invoice_number;
            private String due_date;
            private String invoice_create_date;

            public String getInvoice_create_date() {
                return invoice_create_date;
            }

            public void setInvoice_create_date(String invoice_create_date) {
                this.invoice_create_date = invoice_create_date;
            }

            private String status;
            private String payment_mode;
            private String payment_mode_description;
            private String payment_date;
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

        public static class InvoiceDetailsBean {
            /**
             * description : test
             * amount : 1000
             * tax : 50
             * total_amount : 1500
             */

            private String description;
            private String amount;
            private String tax;
            private String total_amount;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getTax() {
                return tax;
            }

            public void setTax(String tax) {
                this.tax = tax;
            }

            public String getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }
        }
    }
}
