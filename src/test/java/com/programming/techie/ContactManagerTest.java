package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
    ContactManager contactManager;
    @BeforeAll
    public void setupAll(){
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setUp(){
        contactManager =new ContactManager();
    }

    @Test
    public void shouldCreateContact(){
        //ContactManager contactManager =new ContactManager();
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                    .filter(contact ->  contact.getFirstName().equals("John") &&
                                        contact.getLastName().equals("Doe") &&
                                        contact.getPhoneNumber().equals("0123456789"))
                    .findAny().isPresent());
    }

    @Test
    @DisplayName("Shouldn't Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        //ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null,"Doe","0123456789");
        });
    }

    @Test
    @DisplayName("Shouldn't Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        //ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John",null,"0123456789");
        });
    }

    @Test
    @DisplayName("Shouldn't Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        //ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John","Doe",null);
        });
    }

    @AfterEach
    public void tearDown(){
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    public void tearDownAll(){
        System.out.println("Should be excuted at the end of the Test");
    }

    @Test
    @DisplayName("Should Create Contact Only on MacOS")
    @EnabledOnOs(value = OS.MAC,disabledReason = "Enabled on MacOS only")
    public void shouldCreateContactOnMacOSOnly(){
        //ContactManager contactManager =new ContactManager();
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact ->  contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny().isPresent());
    }

    @Test
    @DisplayName("Should Create Contact Only on MacOS")
    @EnabledOnOs(value = OS.WINDOWS,disabledReason = "Enabled on WINDOWS only")
    public void shouldCreateContactOnWindowsOnly(){
        //ContactManager contactManager =new ContactManager();
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact ->  contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny().isPresent());
    }


    /*
    Assumptions (similar to conditional):
    - (Khong thuc thi cac test conditionally, no se perform assertions inside the test conditionally
    - assertions isn't successfull => test won't false -> will be aborted and won't be executed further
    - VD: Co "Test" chi muon chay o may Dev - code duoi => khi chay o may Test => no se abort
    * */
    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shouldTestContactCreationOnDEV(){
        //ContactManager contactManager =new ContactManager();
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
    }

    /*
    Repeat test
    * */
    @DisplayName("Repeat Contact Creation Test 5 Times")
    @RepeatedTest(value = 5, name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
    public void shouldTestContactCreationRepeatedly() {
        contactManager.addContact("John","Doe","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    /*
    Parameterized Test: similar to Repeated Test
    - Run test repeatedly by given diffrent set of input
    * */
    @DisplayName("Value Source Case - Phone Number should match required format")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789","012345678","123456789","+0123456789"})
    public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    @DisplayName("Method Source Case - Phone Number should match required format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456798", "@12345678");
    }

    @DisplayName("CSV Source Case - Phone Number should match required format")
    @ParameterizedTest
    @CsvSource({"0123456789", "0123456798", "0912345678"})
    public void shouldTestContactCreationUsingCSVSource(String phoneNumber) {
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    @DisplayName("CSV File Source Case - Phone Number should match required format")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldTestContactCreationUsingCSVFileSource(String phoneNumber) {
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    /*
    Nested Tests:
    - To group Tests together into a logical group
    - Make the test class more organized
    - Can only use @BeforeEach and AfterEach
    - Can not use @BeforeAll and @AfterAll by default
    */
    @Nested
    class RepeatedNestedTest {
        @DisplayName("Repeat Contact Creation Test 5 Times")
        @RepeatedTest(value = 5, name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        public void shouldTestContactCreationRepeatedly() {
            contactManager.addContact("John","Doe","0123456789");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }
    }

    class ParameterizedNestedTest {
        @DisplayName("Value Source Case - Phone Number should match required format")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789","012345678","123456789","+0123456789"})
        public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
            contactManager.addContact("John","Doe",phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }

        @DisplayName("Method Source Case - Phone Number should match required format")
        @ParameterizedTest
        @MethodSource("phoneNumberList")
        public void shouldTestContactCreationUsingMethodSource(String phoneNumber) {
            contactManager.addContact("John","Doe",phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }

        @DisplayName("CSV Source Case - Phone Number should match required format")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456798", "0912345678"})
        public void shouldTestContactCreationUsingCSVSource(String phoneNumber) {
            contactManager.addContact("John","Doe",phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }

        @DisplayName("CSV File Source Case - Phone Number should match required format")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void shouldTestContactCreationUsingCSVFileSource(String phoneNumber) {
            contactManager.addContact("John","Doe",phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1,contactManager.getAllContacts().size());
        }

    }

    /*
    Disabled Tests: Hạn chế dùng
     */
    @DisplayName("Should Create Contact: Disable Test")
    @Disabled
    public void shouldTestContact() {
        contactManager.addContact("John","Doe","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }
}