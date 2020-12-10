package net.competition.exception;

public enum ServerErrorCode {
    TOKEN_WRONG("Неверный токен"),
    USER_WRONG("Этот логин уже используется"),
    LOGIN_WRONG("Неверный логин или пароль"),
    FIRSTNAME_SHORT("Короткое имя"),
    LASTNAME_SHORT("Короткая фамилия"),
    COMPANY_NAME_SHORT("Короткое название компании"),
    LOGIN_SHORT("Логин должен быть не короче 4 символов"),
    PASSWORD_SHORT("Пароль должен быть не короче 6 символов"),
    LOGIN_EMPTY("Логин не может быть пустым"),
    PASSWORD_EMPTY("Пароль не может быть пустым"),
    APPLICATION_LIST_EMPTY("Список заявок не должен быть пустым"),
    TITLE_APPLICATION_EMPTY("Отсутвует название заявки"),
    DESCRIPTION_APPLICATION_EMPTY("Отсутвует описание заявки"),
    DIRECTIONS_EMPTY("Отсутвует список направлений"),
    SUM_EMPTY("Не указана запрашиваемая сумма гранта"),
    APPLICATION_EMPTY("Отсутствует заявка"),
    RATING_WRONG("Оценка должна быть от 1 до 5"),
    DIRECTION_WRONG("Вы указали направление, по которому не являетесь экспертом"),
    WRONG_JSON("Некорретный json");


    private String errorString;

    ServerErrorCode(String message) {
        errorString = message;
    }

    public String getErrorString() {
        return errorString;
    }
}
