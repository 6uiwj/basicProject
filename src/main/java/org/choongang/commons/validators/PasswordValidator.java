package org.choongang.commons.validators;

public interface PasswordValidator {
    /**
     * 비밀번호에 알파벳 포함 여부
     * @param password
     * @param caseIncensitive
     *          false : 대문자 1개 이상, 소문자 1개 이상 포함해야 함
     *          true: 대소문자 구분없이 1개 이상 포함
     * @return
     */
    default boolean alphaCheck(String password, boolean caseIncensitive) {
        //유연성을 주기 위해 대소문자 구분없이 체크, 대문자소문자 각각포함의 경우로 case 나눠주기
    
    }

    /**
     * 비밀번호에 숫자 포함여부 체크
     * @param password
     * @return
     */
    default boolean numberCheck(String password) {

    }

    /**
     * 비밀번호에 특수문자 포함 여부
     * @param password
     * @return
     */
    default boolean specialCharsCheck(String password){
        
    }

}
