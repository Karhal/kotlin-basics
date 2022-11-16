package encryptdecrypt

import java.io.File

fun main(args:Array<String>) {

    var action  = "enc"
    var message = ""
    var key = 0
    var inFile = ""
    var inData = ""
    var outFile = ""
    var algo = "shift"

    for(i in args.indices) {
        when(args[i]) {
            "-in" -> {
                inFile = args[i+1]
            }
            "-data" -> inData = args[i+1]
            "-mode" -> action = args[i+1]
            "-key" -> key = args[i+1].toInt()
            "-out" -> outFile = args[i+1]
            "-alg" -> algo = args[i+1]
        }
    }

    if(inData != "") {
        message = inData
    } else if(inFile !== "") {

        val fileToRead = File(inFile)

        if(fileToRead.exists()) {
            message = fileToRead.readText()
        } else {
            println("Error no such a file")
        }
    }

    fun encodeUni(message: String): String {

        var s:String = ""

        for(c in message) {
            s += (c.code + key).toChar()
        }

        return s
    }

    fun encodeShift(message: String): String {

        var s:String = ""
        for (i in message.indices) {

            if ((message[i]).code in 97..122) {

                if ((message[i] + key).code > 123) {
                    s += 'a' + ((message[i].code + key) - 123)
                } else {
                    s += message[i] + key
                }
            }else if((message[i]).code in 65..90) {

                if ((message[i] + key).code > 91) {
                    s += 'A' + ((message[i].code + key) - 91)
                } else {
                    s += message[i] + key
                }
            } else {
                s += message[i]
            }
        }

        return s
    }

    fun decodeUni(message: String): String {
        var s:String = ""

        for(c in message) {
            s += (c.code - key).toChar()
        }

        return s

    }

    fun decodeShift(message: String): String {

        var s:String = ""
        for (i in message.indices) {

            if ((message[i]).code in 97..122) {

                if ((message[i] - key).code < 97) {
                    s += (123-('a'.code-((message[i] - key).code))).toChar()
                } else {
                    s += message[i] - key
                }
            }else if((message[i]).code in 65..90) {

                if ((message[i] - key).code < 65) {
                    s += (91-('A'.code-((message[i] - key).code))).toChar()
                } else {
                    s += message[i] - key
                }
            } else {
                s += message[i]
            }
        }

        return s
    }

    fun printMessage(msg:String): Unit {
        if(outFile != "") {
            val printFile = File(outFile)
            printFile.writeText(msg)
        }else {
            println(msg)
        }
    }

    when (action) {
        "enc" -> printMessage(if(algo == "shift") encodeShift(message) else encodeUni(message))
        "dec" -> printMessage(if(algo == "shift") decodeShift(message) else decodeUni(message))
        else -> print("non")
    }
}
