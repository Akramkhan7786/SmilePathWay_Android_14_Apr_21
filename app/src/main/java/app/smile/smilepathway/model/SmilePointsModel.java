package app.smile.smilepathway.model;

import java.util.List;

public class SmilePointsModel {

    /**
     * status : true
     * error : {}
     * result : {"count":"5","overall_balance":361.2,"credit_details":[{"id":"13","debit":null,"credit":"10.2","transaction_type":"credit","type":"Payment","description":null,"created_at":"December 01, 2020 17:22:35"},{"id":"12","debit":null,"credit":"50","transaction_type":"credit","type":"Payment","description":null,"created_at":"December 01, 2020 17:22:01"},{"id":"11","debit":null,"credit":"1","transaction_type":"credit","type":"Payment","description":null,"created_at":"November 30, 2020 17:48:26"},{"id":"10","debit":null,"credit":"50","transaction_type":"credit","type":"Payment","description":null,"created_at":"November 30, 2020 17:48:03"},{"id":"9","debit":null,"credit":"250","transaction_type":"credit","type":"Payment","description":null,"created_at":"November 30, 2020 17:42:58"}],"pagination":{"total_page":1,"current_page":1}}
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
         * count : 5
         * overall_balance : 361.2
         * credit_details : [{"id":"13","debit":null,"credit":"10.2","transaction_type":"credit","type":"Payment","description":null,"created_at":"December 01, 2020 17:22:35"},{"id":"12","debit":null,"credit":"50","transaction_type":"credit","type":"Payment","description":null,"created_at":"December 01, 2020 17:22:01"},{"id":"11","debit":null,"credit":"1","transaction_type":"credit","type":"Payment","description":null,"created_at":"November 30, 2020 17:48:26"},{"id":"10","debit":null,"credit":"50","transaction_type":"credit","type":"Payment","description":null,"created_at":"November 30, 2020 17:48:03"},{"id":"9","debit":null,"credit":"250","transaction_type":"credit","type":"Payment","description":null,"created_at":"November 30, 2020 17:42:58"}]
         * pagination : {"total_page":1,"current_page":1}
         */

        private String count;
        private String  overall_balance;
        private PaginationBean pagination;
        private List<CreditDetailsBean> credit_details;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getOverall_balance() {
            return overall_balance;
        }

        public void setOverall_balance(String overall_balance) {
            this.overall_balance = overall_balance;
        }

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<CreditDetailsBean> getCredit_details() {
            return credit_details;
        }

        public void setCredit_details(List<CreditDetailsBean> credit_details) {
            this.credit_details = credit_details;
        }

        public static class PaginationBean {
            /**
             * total_page : 1
             * current_page : 1
             */

            private int total_page;
            private int current_page;

            public int getTotal_page() {
                return total_page;
            }

            public void setTotal_page(int total_page) {
                this.total_page = total_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }
        }

        public static class CreditDetailsBean {
            /**
             * id : 13
             * debit : null
             * credit : 10.2
             * transaction_type : credit
             * type : Payment
             * description : null
             * created_at : December 01, 2020 17:22:35
             */

            private String id;
            private String debit;
            private String credit;
            private String transaction_type;
            private String type;
            private String description;
            private String created_at;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDebit() {
                return debit;
            }

            public void setDebit(String debit) {
                this.debit = debit;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getTransaction_type() {
                return transaction_type;
            }

            public void setTransaction_type(String transaction_type) {
                this.transaction_type = transaction_type;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }
        }
    }
}
