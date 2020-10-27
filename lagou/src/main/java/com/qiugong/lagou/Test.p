Classfile /Users/meiyou/Desktop/Book/lagou/src/main/java/com/qiugong/lagou/Test.class
  Last modified 2020-10-27; size 351 bytes
  MD5 checksum 02db7fabc1fe7183cbfdfc49097dba1e
  Compiled from "Test.java"
public class Test implements java.io.Serializable,java.lang.Cloneable
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#17         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#18         // Test.num:I
   #3 = Class              #19            // Test
   #4 = Class              #20            // java/lang/Object
   #5 = Class              #21            // java/io/Serializable
   #6 = Class              #22            // java/lang/Cloneable
   #7 = Utf8               num
   #8 = Utf8               I
   #9 = Utf8               <init>
  #10 = Utf8               ()V
  #11 = Utf8               Code
  #12 = Utf8               LineNumberTable
  #13 = Utf8               add
  #14 = Utf8               (I)I
  #15 = Utf8               SourceFile
  #16 = Utf8               Test.java
  #17 = NameAndType        #9:#10         // "<init>":()V
  #18 = NameAndType        #7:#8          // num:I
  #19 = Utf8               Test
  #20 = Utf8               java/lang/Object
  #21 = Utf8               java/io/Serializable
  #22 = Utf8               java/lang/Cloneable
{
  public Test();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: iconst_1
         6: putfield      #2                  // Field num:I
         9: return
      LineNumberTable:
        line 3: 0
        line 5: 4

  public int add(int);
    descriptor: (I)I
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=3, args_size=2
         0: bipush        10
         2: istore_2
         3: aload_0
         4: aload_0
         5: getfield      #2                  // Field num:I
         8: iload_1
         9: iadd
        10: putfield      #2                  // Field num:I
        13: aload_0
        14: getfield      #2                  // Field num:I
        17: ireturn
      LineNumberTable:
        line 8: 0
        line 9: 3
        line 10: 13
}
SourceFile: "Test.java"
