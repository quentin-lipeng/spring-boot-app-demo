package org.quentin.springbootredisapp.vo;

public class CommonResponse<D> {
    private D data;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
