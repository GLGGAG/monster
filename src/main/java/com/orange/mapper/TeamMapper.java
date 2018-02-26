package com.orange.mapper;

import com.orange.entity.Team;
import com.orange.vos.TeamVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/上午 11:29
 * @Description:
 */
public interface TeamMapper {

    List<TeamVo> selectTeamVoGroupByTeamNo();

    int insertTeam(Team team);

    int deletedUserByUserNoTeamNo(@Param("userNo") Integer userNo, @Param("teamNo")Integer teamNo);
}
