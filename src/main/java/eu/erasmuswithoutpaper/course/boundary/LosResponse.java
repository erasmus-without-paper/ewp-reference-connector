package eu.erasmuswithoutpaper.course.boundary;

import eu.erasmuswithoutpaper.common.boundary.BaseClientResponse;
import java.io.Serializable;
import java.util.List;

public class LosResponse extends BaseClientResponse implements Serializable {
    private List<? extends Object> resultList;

    public List<? extends Object> getResultList() {
        return resultList;
    }

    public void setResultList(List<? extends Object> resultList) {
        this.resultList = resultList;
    }

}
