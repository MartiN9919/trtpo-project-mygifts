package com.example.mygifts.logic

import java.beans.IntrospectionException
import java.io.InputStream
import java.lang.reflect.InvocationTargetException

import com.sun.security.ntlm.Client
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.expression.ParseException
import org.springframework.web.multipart.MultipartFile

import com.core.common.BaseService
import com.po.Client

class ContactService : BaseService<Contact, Int> {
    fun vagueFind(contant: String, currentpage: Int, pagesize: Int): List<Client>

    @Throws(Exception::class)
    fun importExcelInfo(`in`: InputStream, file: MultipartFile)

    @Throws(
        InvocationTargetException::class,
        ClassNotFoundException::class,
        IntrospectionException::class,
        ParseException::class,
        IllegalAccessException::class
    )
    fun exportExcelInfo(): XSSFWorkbook
}