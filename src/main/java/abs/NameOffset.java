package abs;

public class NameOffset {
    private String name;
    private int offset; 

    public NameOffset(String name, int offset){
        this.name = name; 
        this.offset = offset;
    }

    public String get_name(){ 
        return this.name; 
    }
    public int get_offset(){ 
        return this.offset; 
    }
}
