package mvc.dao;

import mvc.model.Spike;
import org.springframework.stereotype.Repository;

/**
 * 秒杀的数据操作层
 * Created by Administrator on 2017/8/13.
 */
@Repository
public class SpikeDao extends BaseDao{

        public void addSpike(Spike spike){
            String sql="SpikeMapper.addSpike";
            this.getSqlSession().insert(sql,spike);
        }
}
