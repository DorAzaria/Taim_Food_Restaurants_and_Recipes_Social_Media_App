package food.taim;

public class RecipeChart {

    private String recipeName;
    private String infoRecipeName;
    private int Photo;

    public RecipeChart() {

    }

    public RecipeChart(String recipeName, String infoRecipeName, int photo) {
        this.recipeName = recipeName;
        this.infoRecipeName = infoRecipeName;
        Photo = photo;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getInfoRecipeName() {
        return infoRecipeName;
    }

    public void setInfoRecipeName(String infoRecipeName) {
        this.infoRecipeName = infoRecipeName;
    }

    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }
}
