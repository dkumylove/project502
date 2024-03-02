package org.choongang.commons.validators;

public interface PasswordValidator {


    /**
     * 비밀번호에 알파벳 포함여부
     * @param password
     * @param caseIncensitive
     *      false : 대문자 1개 이상, 소문자 1개 이상 포함
     *      true : 대소문자 구분없이 1개 이상 포함
     * @return
     */
    default boolean alphaCheck(String password, boolean caseIncensitive) {
        if(caseIncensitive) { // 대소문자 구분 없이 체크
            String pattern = ".*[a-zA-Z]+.*";

            return password.matches(pattern);
        } else { // 대문자 1개, 소문자 1개 포함
            String pattern1 = ".*[a-z]+.*";
            String pattern2 = ".*[A-Z]+.*";

            return password.matches(pattern1) && password.matches(pattern2);
        }
    }

    /**
     * 비밀번호에 숫자 포함여부
     * \d = 숫자를 의미함
     * @param password
     * @return
     */
    default boolean numberCheck(String password){
        //return password.matches(".*[0-9]+.*");
        return password.matches(".*\\d+.*");
    }

    /**
     * 비밀번호에 특수문자 포함여부
     * @param password
     * @return
     */
    default boolean specialcharsCheck(String password){
        String pattern = ".*[`~!@#$%^&*()-_+=]+.*";

        return password.matches(pattern);
    }
}
