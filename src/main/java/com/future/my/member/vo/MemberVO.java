package com.future.my.member.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.future.my.common.valid.Login;
import com.future.my.common.valid.Regist;

public class MemberVO {
   
   //2024-10-17 NotEmpty, pattern size
  
	@NotEmpty(message="아이디 필수!", groups= {Login.class, Regist.class})
   private String memId;
   
   //   \\w -> [a-zA-Z0-9]
   @Pattern(regexp ="^\\w{4,10}$", message="패스워드는 영문 숫자 4-10 로 입력!!",groups= {Login.class, Regist.class})
   private String memPw;
   @Size(min=1, max=20, message="이름은 20자 이내로 압력!!",groups= {Regist.class})
   private String memNm;
   
   private String memAddr;
   private String profileImg;
   
   public String getMemId() {
      return memId;
   }
   public void setMemId(String memId) {
      this.memId = memId;
   }
   public String getMemPw() {
      return memPw;
   }
   public void setMemPw(String memPw) {
      this.memPw = memPw;
   }
   public String getMemNm() {
      return memNm;
   }
   public void setMemNm(String memNm) {
      this.memNm = memNm;
   }
   public String getMemAddr() {
      return memAddr;
   }
   public void setMemAddr(String memAddr) {
      this.memAddr = memAddr;
   }
   public String getProfileImg() {
      return profileImg;
   }
   public void setProfileImg(String profileImg) {
      this.profileImg = profileImg;
   }
   @Override
   public String toString() {
      return "MemberVO [memId=" + memId + ", memPw=" + memPw + ", memNm=" + memNm + ", memAddr=" + memAddr
            + ", profileImg=" + profileImg + "]";
   }
   
   

}

