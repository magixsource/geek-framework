package gl.linpeng.gf.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class PageInfo<T> implements Serializable {

    public Integer page;
    public Integer pageSize;
    public Long total;
    public Collection<T> list;

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

    public Collection<T> getList() {
        return list;
    }

    public void setList(Collection<T> list) {
        this.list = list;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> retVal = new TreeMap();
        retVal.put("page", this.getPage());
        retVal.put("pageSize", this.getPageSize());
        retVal.put("total", this.getTotal());
        retVal.put("list", this.getList());
        return retVal;
    }
}
