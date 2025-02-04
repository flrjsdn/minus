package com.hexa.muinus.common.validator;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.dto.StoreModifyDTO;
import com.hexa.muinus.store.dto.StoreRegisterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * When Store Register, 
 * 플리마켓 허용 시 플리마켓 관련 데이터 Validation
 */
public class StoreFlimarketValidatorImpl implements ConstraintValidator<StoreFlimarketValidator, Object> {

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        if (dto instanceof StoreRegisterDTO registerDTO) {
            return validateFlimarketFields(registerDTO, context);
        } else if (dto instanceof StoreModifyDTO modifyDTO) {
            return validateFlimarketFields(modifyDTO, context);
        }
        return true;
    }

    private boolean validateFlimarketFields(StoreRegisterDTO registerDTO, ConstraintValidatorContext context) {
        // 플리마켓이 "Y"일 때
        // flimarketImageUrl flimarketSectionCnt도 필수
        if (registerDTO.getFlimarketYn() == YesNo.Y) {
            boolean imageValid = registerDTO.getFlimarketImageUrl() != null;
            boolean sectionValid = registerDTO.getFlimarketSectionCnt() != null;

            if (!imageValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("플리마켓이 활성화된 경우 'flimarketImageUrl'는 필수입니다.")
                        .addPropertyNode("flimarketImageUrl")
                        .addConstraintViolation();
            }

            if (!sectionValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("플리마켓이 활성화된 경우 'flimarketSectionCnt'는 필수입니다.")
                        .addPropertyNode("flimarketSectionCnt")
                        .addConstraintViolation();
            }
            return imageValid && sectionValid;
        }

        // 플리마켓이 "N"일 때는 나머지 Null 이어도 됨
        return true;
    }

    private boolean validateFlimarketFields(StoreModifyDTO modifyDTO, ConstraintValidatorContext context) {
        // 플리마켓이 "Y"일 때
        // flimarketImageUrl flimarketSectionCnt도 필수
        if (modifyDTO.getFlimarketYn() == YesNo.Y) {
            boolean imageValid = modifyDTO.getFlimarketImageUrl() != null;
            boolean sectionValid = modifyDTO.getFlimarketSectionCnt() != null;

            if (!imageValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("플리마켓이 활성화된 경우 'flimarketImageUrl'는 필수입니다.")
                        .addPropertyNode("flimarketImageUrl")
                        .addConstraintViolation();
            }

            if (!sectionValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("플리마켓이 활성화된 경우 'flimarketSectionCnt'는 필수입니다.")
                        .addPropertyNode("flimarketSectionCnt")
                        .addConstraintViolation();
            }
            return imageValid && sectionValid;
        }

        // 플리마켓이 "N"일 때는 나머지 Null 이어도 됨
        return true;
    }
}
