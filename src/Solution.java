public class Solution {
    private String url;
    private String question, answer, category;
    private double cost;

    public Solution(String url, String question,
                    String category, String answer, double cost){
        setUrl(url);
        setQuestion(question);
        setAnswer(answer);
        setCategory(category);
        setCost(cost);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if(category == null || category.trim().isEmpty()){
            throw new IllegalArgumentException("a mattie, kijk " +
                    "die categorie");
        }
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(url == null || url.trim().isEmpty()){
            throw new IllegalArgumentException("url kapoet, vul iet in");
        }
        this.url = url;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        if(question == null || question.trim().isEmpty()){
            throw new IllegalArgumentException("Probleempie bij " +
                    "de question ma nigga");
        }
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        if(answer == null || answer.trim().isEmpty()){
            throw new IllegalArgumentException("probleempie bij het " +
                    "antwoord ma dude");
        }
        this.answer = answer;
    }
}
