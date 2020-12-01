package com.helix.demo.rpc.grpc.serializable;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 此测试AddressBook结构是根据/resources/com/helix/demo/rpc/grpc/proto/addressbook.proto协议由代码自动生成的。
 * 生成的代码在target/generated-test-sources/protobuf/目录下面
 * @author lijianyu@yunloan.net
 * @date 2020-11-20 14:50
 */
public class ProtobufTest {

    private AddressBookProtos.AddressBook addressBook;

     {
         //初始化addressbook和person
        AddressBookProtos.Person person =
                AddressBookProtos.Person.newBuilder()
                        .setId(1234)
                        .setName("John Doe")
                        .setEmail("jdoe@example.com")
                        .addPhones(
                                AddressBookProtos.Person.PhoneNumber.newBuilder()
                                        .setNumber("555-4321")
                                        .setType(AddressBookProtos.Person.PhoneType.HOME))
                        .build();

        addressBook = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();

    }

    /**
     * protobuf协议 序列化/反序列
     * @throws IOException
     */
    @Test
    public void serialize() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        addressBook.writeTo(bos);

        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.parseFrom(bos.toByteArray());
        System.out.println(addressBook.getPeopleCount());
        System.out.println(addressBook.getPeople(0).getName());
    }
}
