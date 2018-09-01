package gl.linpeng.gf.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class PageInfo<T> implements Serializable {

    public Integer page;
    public Integer pageSize;
    public Long total;
    public List<T> list;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
