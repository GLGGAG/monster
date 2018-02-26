package com.orange.mapper;

import com.orange.entity.User;
import com.orange.vos.TeamUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/04/下午 20:06
 * @Description:
 */
public interface UserMapper {

    User selectUserByUserPhone(@Param("userPhone") String userPhone);


    User selectUserByUserNo(@Param("userNo") Integer userNo);

    List<User> selectUserListByTeamNoExcludeUserNo(@Param("userNo")Integer userNo,@Param("teamNo") Integer teamNo);

    List<User> selectUserListByTeamNo(@Param("teamNo")Integer teamNo);

    int insertUser(User user);

    int deletedUserByUserNo(@Param("userNo")Integer userNo);

    List<TeamUserVo> selectTeamUserList();

    List<TeamUserVo> selectTeamUserListExcludeUserNo(@Param("userNo")Integer userNo);



}
