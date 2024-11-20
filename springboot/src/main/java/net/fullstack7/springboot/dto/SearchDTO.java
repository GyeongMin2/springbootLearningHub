package net.fullstack7.springboot.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String searchType = "all";
    private String keyword = "";
    private int page = 1;
    
    public int getOffset() {
        return (page - 1) * 10;
    }
}
