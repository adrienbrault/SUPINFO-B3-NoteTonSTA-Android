package fr.adrienbrault.notetonsta.entity;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 17:20
 */

public class Evaluation {

    protected Integer id;

    protected Intervention intervention;

    protected Integer idBooster;

    protected String comment;

    protected Float speakerKnowledgeMark;

    protected Float speakerTeachingMark;

    protected Float speakerAnswersMark;

    protected Float slidesContentMark;

    protected Float slidesFormatMark;

    protected Float slidesExamplesMark;

    public Evaluation() {

    }

    public Evaluation(Intervention intervention) {
        setIntervention(intervention);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    public Integer getIdBooster() {
        return idBooster;
    }

    public void setIdBooster(Integer idBooster) {
        this.idBooster = idBooster;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getSpeakerKnowledgeMark() {
        return speakerKnowledgeMark;
    }

    public void setSpeakerKnowledgeMark(Float speakerKnowledgeMark) {
        this.speakerKnowledgeMark = speakerKnowledgeMark;
    }

    public Float getSpeakerTeachingMark() {
        return speakerTeachingMark;
    }

    public void setSpeakerTeachingMark(Float speakerTeachingMark) {
        this.speakerTeachingMark = speakerTeachingMark;
    }

    public Float getSpeakerAnswersMark() {
        return speakerAnswersMark;
    }

    public void setSpeakerAnswersMark(Float speakerAnswersMark) {
        this.speakerAnswersMark = speakerAnswersMark;
    }

    public Float getSlidesContentMark() {
        return slidesContentMark;
    }

    public void setSlidesContentMark(Float slidesContentMark) {
        this.slidesContentMark = slidesContentMark;
    }

    public Float getSlidesFormatMark() {
        return slidesFormatMark;
    }

    public void setSlidesFormatMark(Float slidesFormatMark) {
        this.slidesFormatMark = slidesFormatMark;
    }

    public Float getSlidesExamplesMark() {
        return slidesExamplesMark;
    }

    public void setSlidesExamplesMark(Float slidesExamplesMark) {
        this.slidesExamplesMark = slidesExamplesMark;
    }

}
