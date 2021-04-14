package app.smile.smilepathway.model;

public class ChatDetailsModel {
    /**
     * chat_room : SM_10_162_101773
     * chat_key : d100f405a66ca78512431db75ea2f391.193f2ae57bd13421587ac66a504f001d.193f2ae57bd13421587ac66a504f001d
     * name : Mike Parker
     * user_type : patient
     */

    private String chat_room;
    private String chat_key;
    private String name;
    private String user_type;

    public String getChat_room() {
        return chat_room;
    }

    public void setChat_room(String chat_room) {
        this.chat_room = chat_room;
    }

    public String getChat_key() {
        return chat_key;
    }

    public void setChat_key(String chat_key) {
        this.chat_key = chat_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
