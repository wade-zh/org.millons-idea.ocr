/***
 * @pName mi-ocr-web-user
 * @name IBackcategoryMapperRepository
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.millions.idea.ocr.web.user.entity.db.Backcategorys;

import java.util.List;

@Mapper
public interface IBackcategoryMapperRepository {

    /**
     * Query all categorys
     * @return
     */
    List<Backcategorys> selectAll();
}
