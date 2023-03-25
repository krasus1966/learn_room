package top.krasus1966.contactlist.domain

enum class SortType(val column:String,val value:String) {
    NAME("name","姓名"),
    NICK_NAME("nickName","昵称"),
    PHONE_NUMBER("phoneNumber","手机号码");
}