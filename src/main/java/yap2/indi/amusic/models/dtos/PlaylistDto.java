package yap2.indi.amusic.models.dtos;

public class PlaylistDto {

    private Long id;
    private String name;
    private String description;
    private Long totalDuration;
    public PlaylistDto(Long id, String name, String description, Long totalDuration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalDuration = totalDuration;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getTotalDuration() {
        return totalDuration;
    }
    public void setTotalDuration(Long totalDuration) {
        this.totalDuration = totalDuration;
    }
}
