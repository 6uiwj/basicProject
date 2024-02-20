package org.choongang.commons.validators;

public interface PasswordValidator {
    /**
     * 비밀번호에 알파벳 포함 여부
     * @param password
     * @param caseIncensitive
     *          false : 대문자 1개 이상, 소문자 1개 이상 포함해야 함
     *          true: 대소문자 구분없이 1개 이상 포함 -> 따로 체크X
     * @return
     */
    default boolean alphaCheck(String password, boolean caseIncensitive) {
        //유연성을 주기 위해 대소문자 구분없이 체크, 대문자소문자 각각포함의 경우로 case 나눠주기
        if (caseIncensitive) { //대소문자 구분없이 체크
            //나중에 정규표현식으로 바꿔주기
            //1개이상의 대소문자를 포함하고 앞뒤로는 있어도 되고, 없어도 됨?
            String pattern = ".*[a-zA-z]+.*";
            return password.matches(pattern);

        } else { //대문자 1개, 소문자 1개 포함
            String pattern1 = ".*[a-z]+.*"; //소문자
            String pattern2 = ".*[A-Z]+.*"; //대문자

            return password.matches(pattern1) && password.matches(pattern2);
        }
    }

    /**
     * 비밀번호에 숫자 포함여부 체크
     * @param password
     * @return
     */
    default boolean numberCheck(String password) {
        return password.matches(".*\\d+.*"); // == .*[0-9]+.*
    }

    /**
     * 비밀번호에 특수문자 포함 여부
     * @param password
     * @return
     */
    default boolean specialCharsCheck(String password){
        String pattern = ".*[`~!@#$%^*&()-_=]+.*";

        return password.matches(pattern);
    }

}
