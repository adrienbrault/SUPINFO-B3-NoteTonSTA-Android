package fr.adrienbrault.notetonsta.entity;

import java.util.Date;
import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 17:18
 */

public class Intervention {

    protected Integer id;

    protected Campus campus;

    protected String subject;

    protected String description;

    protected Date dateBegin;

    protected Date dateEnd;

    protected Speaker speaker;

    protected List<Evaluation> evaluations;

    protected Double speakerAverageMark;

    protected Double slidesAverageMark;

    protected Double averageMark;

    protected Integer evaluationsCount;

    public Intervention() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public Double getSpeakerAverageMark() {
        return speakerAverageMark;
    }

    public void setSpeakerAverageMark(Double speakerAverageMark) {
        this.speakerAverageMark = speakerAverageMark;
    }

    public Double getSlidesAverageMark() {
        return slidesAverageMark;
    }

    public void setSlidesAverageMark(Double slidesAverageMark) {
        this.slidesAverageMark = slidesAverageMark;
    }

    public Double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Double averageMark) {
        this.averageMark = averageMark;
    }

    public Integer getEvaluationsCount() {
        return evaluationsCount;
    }

    public void setEvaluationsCount(Integer evaluationsCount) {
        this.evaluationsCount = evaluationsCount;
    }

    public String getStatusString() {
        Date now = new Date();
        String status;

        if (dateBegin.before(now)) {
            status = "Ended";
        } else if (dateEnd.after(now)) {
            status = "To begin";
        } else {
            status = "In progress";
        }

        return status;
    }

}
