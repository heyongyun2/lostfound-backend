package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.springboot.entity.Claim;

import java.util.List;

public interface ClaimMapper {
    int insertClaim(Claim claim);
    List<Claim> selectAllClaims();

    // 【新增】根据 ID 更新认领状态
    int updateStatus(@Param("claimId") Integer claimId, @Param("status") String status);
    List<Claim> selectClaimHistory();
    List<Claim> selectClaimsWithItemByUserId(@Param("userId") Long userId);
    int deleteById(@Param("claimId") Long claimId);
}
