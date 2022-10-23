package cn.auroralab.devtrack.vo;

import cn.auroralab.devtrack.entity.Account;
import lombok.Data;

@Data
public class EditProfileResultVO extends ResultVO<Account> {
     public EditProfileResultVO(StatusCodeEnum statusCodeEnum){ super(statusCodeEnum);}
     public EditProfileResultVO(StatusCodeEnum statusCodeEnum,Account account){ super(statusCodeEnum,account);}
}
