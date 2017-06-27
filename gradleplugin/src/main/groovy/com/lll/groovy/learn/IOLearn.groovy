class IOLearn {

    /**
     * copy文件的方法，方法可以不用显示指定参数的类型。同时还可以支持默认参数：
     *      示例：desFile='E:/dsss/es.txt'
     * @param sourceFile
     * @param desFile
     */
    void copyFile(sourceFile, desFile) {
        def src = new File(sourceFile);
        if (src.exists()) {//文件不存在
            //输出文件路径和大小
            println "The file ${src.absolutePath} has ${src.length()} bytes"

            def des = new File(desFile)
            if(!des.exists()){//文件不存在，创建文件
                des.createNewFile();
            }
            des << src.text;//就实现了文件copy
        }

    }

    /**
     * 函数入口
     * @param args
     */
    static void main(String[] args) {

    }
}