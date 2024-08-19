package io.ylab.common.reflection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.type.TypeKind;
import javax.management.ObjectName;
import javax.naming.CompositeName;
import javax.naming.CompoundName;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.SSLEngineResult;
import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.text.html.FormSubmitEvent;
import javax.tools.Diagnostic;
import javax.tools.DocumentationTool;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.AddressingFeature;
import java.awt.*;
import java.awt.font.NumericShaper;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectStreamField;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.management.MemoryType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.Authenticator;
import java.net.Proxy;
import java.net.StandardProtocolFamily;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.nio.file.AccessMode;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.security.CryptoPrimitive;
import java.security.KeyRep;
import java.security.cert.CRLReason;
import java.security.cert.CertPathValidatorException;
import java.security.cert.PKIXReason;
import java.security.cert.PKIXRevocationChecker;
import java.sql.ClientInfoStatus;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PseudoColumnUsage;
import java.sql.RowIdLifetime;
import java.sql.Timestamp;
import java.text.CollationKey;
import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahEra;
import java.time.chrono.IsoChronology;
import java.time.chrono.IsoEra;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.chrono.MinguoEra;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OfStringFactoryTest {
    @ParameterizedTest
    @MethodSource("positiveEnumTestDataProvider")
    <T> void create_validEnumInput_createObjectOfExpectedClass(
            Class<T> tClass, String value, T expected
    ) {
        final OfStringFactory<T> factory = new OfStringFactory<>(tClass, value);
        final Optional<T> actualInstance = factory.create();

        assertAll(
                () -> assertTrue(actualInstance.isPresent()),
                () -> assertEquals(expected, actualInstance.get())
        );

    }

    @ParameterizedTest
    @MethodSource("positiveTestDataProvider")
    <T> void create_validInput_createObjectOfExpectedClass(Class<T> tClass, String value) {
        final OfStringFactory<T> factory = new OfStringFactory<>(tClass, value);
        final Optional<T> actualInstance = factory.create();

        assertAll(
                () -> assertTrue(actualInstance.isPresent()),
                () -> assertEquals(tClass, actualInstance.get().getClass())
        );

    }

    @ParameterizedTest
    @MethodSource("negativeDataTestDataProvider")
    <T> void create_invalidInput_returnEmptyOptional(Class<T> tClass, String value) {
        final OfStringFactory<T> factory = new OfStringFactory<>(tClass, value);
        final Optional<T> instance = factory.create();

        assertTrue(!instance.isPresent());
    }

    @ParameterizedTest
    @MethodSource("negativeDataTestDataProvider")
    <T> void create_noParsingFabricMethod_returnEmptyOptional(Class<T> tClass, String value) {
        final OfStringFactory<T> factory = new OfStringFactory<>(tClass, value);
        final Optional<T> instance = factory.create();

        assertTrue(!instance.isPresent());
    }

    static Stream<Arguments> positiveEnumTestDataProvider() {
        return Stream.of(
                arguments(TimeUnit.class, "SECONDS", TimeUnit.SECONDS),
                arguments(DayOfWeek.class, "MONDAY", DayOfWeek.MONDAY)
        );
    }

    static Stream<Arguments> positiveTestDataProvider() {
        return Stream.of(
                arguments(Duration.class, "PT1H2M3S"),
                arguments(Instant.class, "2023-12-28T10:15:30Z"),
                arguments(LocalDate.class, "2023-12-28"),
                arguments(LocalDateTime.class, "2023-12-28T10:15:30"),
                arguments(LocalTime.class, "10:15:30"),
                arguments(MonthDay.class, "--12-28"),
                arguments(OffsetDateTime.class, "2023-12-28T10:15:30+02:00"),
                arguments(OffsetTime.class, "10:15:30+02:00"),
                arguments(Year.class, "2023"),
                arguments(YearMonth.class, "2023-12"),
                arguments(ZonedDateTime.class, "2023-12-28T10:15:30+02:00[Europe/Moscow]"),
                arguments(BigDecimal.class, "123.45"),
                arguments(BigInteger.class, "1234567890"),
                arguments(Boolean.class, "true"),
                arguments(URI.class, "https://www.example.com"),
                arguments(URL.class, "https://www.example.com"),
                arguments(CompositeName.class, "comp/env/jdbc/mydatasource"),
                arguments(LdapName.class, "cn=John Doe,ou=People,dc=example,dc=com"),
                arguments(ObjectName.class, "com.example:type=MyType,name=MyName"),
                arguments(ZoneOffset.class, "+02:00"),
                arguments(AccessMode.class, "READ"),
                arguments(AclEntryFlag.class, "FILE_INHERIT"),
                arguments(AclEntryPermission.class, "READ_DATA"),
                arguments(AclEntryType.class, "ALLOW"),
                arguments(AddressingFeature.Responses.class, "ANONYMOUS"),
                arguments(Authenticator.RequestorType.class, "PROXY"),
                arguments(Byte.class, "123"),
                arguments(CertPathValidatorException.BasicReason.class, "ALGORITHM_CONSTRAINED"),
                arguments(Character.UnicodeScript.class, "LATIN"),
                arguments(ChronoField.class, "YEAR"),
                arguments(ChronoUnit.class, "DAYS"),
                arguments(ClientInfoStatus.class, "REASON_UNKNOWN"),
                arguments(Collector.Characteristics.class, "CONCURRENT"),
                arguments(Component.BaselineResizeBehavior.class, "CONSTANT_ASCENT"),
                arguments(CRLReason.class, "UNSPECIFIED"),
                arguments(CryptoPrimitive.class, "MESSAGE_DIGEST"),
                arguments(XmlNsForm.class, "QUALIFIED"),
                arguments(java.util.Date.class, "Dec 28 2023"),
                arguments(java.sql.Date.class, "2023-12-28"),
                arguments(DayOfWeek.class, "MONDAY"),
                arguments(Desktop.Action.class, "BROWSE"),
                arguments(Diagnostic.Kind.class, "ERROR"),
                arguments(Dialog.ModalExclusionType.class, "NO_EXCLUDE"),
                arguments(Dialog.ModalityType.class, "APPLICATION_MODAL"),
                arguments(DocumentationTool.Location.class, "DOCUMENTATION_OUTPUT"),
                arguments(Double.class, "123.45"),
                arguments(DropMode.class, "INSERT"),
                arguments(ElementKind.class, "PACKAGE"),
                arguments(ElementType.class, "TYPE"),
                arguments(File.class, "test.txt"),
                arguments(FileVisitOption.class, "FOLLOW_LINKS"),
                arguments(FileVisitResult.class, "CONTINUE"),
                arguments(Float.class, "123.45"),
                arguments(FormatStyle.class, "SHORT"),
                arguments(Formatter.BigDecimalLayoutForm.class, "DECIMAL_FLOAT"),
                arguments(FormSubmitEvent.MethodType.class, "GET"),
                arguments(GraphicsDevice.WindowTranslucency.class, "PERPIXEL_TRANSLUCENT"),
                arguments(GroupLayout.Alignment.class, "LEADING"),
                arguments(HijrahEra.class, "AH"),
                arguments(Integer.class, "123"),
                arguments(IsoEra.class, "CE"),
                arguments(JavaFileObject.Kind.class, "SOURCE"),
                arguments(JDBCType.class, "VARCHAR"),
                arguments(JTable.PrintMode.class, "FIT_WIDTH"),
                arguments(KeyRep.Type.class, "SECRET"),
                arguments(LayoutStyle.ComponentPlacement.class, "RELATED"),
                arguments(LinkOption.class, "NOFOLLOW_LINKS"),
                arguments(Locale.Category.class, "DISPLAY"),
                arguments(Locale.FilteringMode.class, "AUTOSELECT_FILTERING"),
                arguments(Long.class, "1234567890"),
                arguments(MemoryType.class, "HEAP"),
                arguments(MessageContext.Scope.class, "APPLICATION"),
                arguments(MinguoEra.class, "ROC"),
                arguments(Modifier.class, "PUBLIC"),
                arguments(Month.class, "DECEMBER"),
                arguments(MultipleGradientPaint.ColorSpaceType.class, "SRGB"),
                arguments(MultipleGradientPaint.CycleMethod.class, "NO_CYCLE"),
                arguments(NestingKind.class, "TOP_LEVEL"),
                arguments(Normalizer.Form.class, "NFC"),
                arguments(NumericShaper.Range.class, "EUROPEAN"),
                arguments(PKIXReason.class, "NAME_CHAINING"),
                arguments(PKIXRevocationChecker.Option.class, "ONLY_END_ENTITY"),
                arguments(PosixFilePermission.class, "OWNER_READ"),
                arguments(ProcessBuilder.Redirect.Type.class, "PIPE"),
                arguments(Proxy.Type.class, "HTTP"),
                arguments(PseudoColumnUsage.class, "USAGE_UNKNOWN"),
                arguments(Rdn.class, "cn=John Doe"),
                arguments(ResolverStyle.class, "STRICT"),
                arguments(Resource.AuthenticationType.class, "CONTAINER"),
                arguments(RetentionPolicy.class, "RUNTIME"),
                arguments(RoundingMode.class, "HALF_UP"),
                arguments(RowFilter.ComparisonType.class, "EQUAL"),
                arguments(RowIdLifetime.class, "ROWID_VALID_FOREVER"),
                arguments(RowSorterEvent.Type.class, "SORT_ORDER_CHANGED"),
                arguments(Service.Mode.class, "MESSAGE"),
                arguments(Short.class, "123"),
                arguments(SignStyle.class, "ALWAYS"),
                arguments(SOAPBinding.ParameterStyle.class, "BARE"),
                arguments(SOAPBinding.Style.class, "DOCUMENT"),
                arguments(SOAPBinding.Use.class, "LITERAL"),
                arguments(SortOrder.class, "ASCENDING"),
                arguments(SourceVersion.class, "RELEASE_8"),
                arguments(SSLEngineResult.HandshakeStatus.class, "FINISHED"),
                arguments(SSLEngineResult.Status.class, "OK"),
                arguments(StandardCopyOption.class, "REPLACE_EXISTING"),
                arguments(StandardLocation.class, "CLASS_PATH"),
                arguments(StandardOpenOption.class, "CREATE"),
                arguments(StandardProtocolFamily.class, "INET"),
                arguments(String.class, "test"),
                arguments(SwingWorker.StateValue.class, "DONE"),
                arguments(TextStyle.class, "FULL"),
                arguments(ThaiBuddhistEra.class, "BE"),
                arguments(Thread.State.class, "RUNNABLE"),
                arguments(java.sql.Time.class, "10:15:30"),
                arguments(Timestamp.class, "2023-12-28 10:15:30.0"),
                arguments(TrayIcon.MessageType.class, "INFO"),
                arguments(TypeKind.class, "INT"),
                arguments(UUID.class, "f81d4fae-7dec-11d0-a765-00a0c91e6bf6"),
                arguments(WebParam.Mode.class, "IN"),
                arguments(Window.Type.class, "NORMAL"),
                arguments(XmlAccessOrder.class, "ALPHABETICAL"),
                arguments(XmlAccessType.class, "FIELD"),
                arguments(ZoneOffsetTransitionRule.TimeDefinition.class, "STANDARD")
        );
    }

    static Stream<Arguments> negativeAbsentFabricMethodTestDataProvider() {
        return Stream.of(
                arguments(Point2D.class, "10,20"),
                arguments(Color.class, "red"),
                arguments(BufferedImage.class, "image.png"),
                arguments(Random.class, "seed"),
                arguments(InputStream.class, "input.txt"),
                arguments(OutputStream.class, "output.txt"),
                arguments(Connection.class, "jdbc:mysql://localhost:3306/mydb"),
                arguments(Pattern.class, "[a-z]+"),
                arguments(Thread.class, "MyThread"),
                arguments(Scanner.class, "input string")
        );
    }

    static Stream<Arguments> negativeDataTestDataProvider() {
        return Stream.of(
                arguments(AbstractChronology.class, "ISO"),
                arguments(ByteBuffer.class, "test"),
                arguments(Calendar.class, "2023-12-28T10:15:30Z"),
                arguments(Character.class, "A"),
                arguments(CollationKey.class, "test"),
                arguments(CompoundName.class, "cn=John Doe,ou=People,dc=example,dc=com"),
                arguments(DoubleBuffer.class, "test"),
                arguments(FileTime.class, "1672216930000000000"),
                arguments(FloatBuffer.class, "test"),
                arguments(HijrahChronology.class, "Hijrah-umalqura"),
                arguments(HijrahDate.class, "1445-05-10"),
                arguments(IntBuffer.class, "test"),
                arguments(IsoChronology.class, "ISO"),
                arguments(JapaneseChronology.class, "Japanese"),
                arguments(JapaneseDate.class, "Reiwa 5-12-28"),
                arguments(LongBuffer.class, "test"),
                arguments(MappedByteBuffer.class, "test"),
                arguments(MinguoChronology.class, "Minguo"),
                arguments(MinguoDate.class, "112-12-28"),
                arguments(ObjectStreamField.class, "test"),
                arguments(ShortBuffer.class, "test"),
                arguments(ThaiBuddhistChronology.class, "ThaiBuddhist"),
                arguments(ThaiBuddhistDate.class, "2566-12-28"),
                arguments(ZoneOffsetTransition.class, "2023-12-28T02:00+02:00[Europe/Moscow]-2024-03-31T03:00+03:00[Europe/Moscow]"),
                arguments(BigDecimal.class, "0x123.45"),
                arguments(BigInteger.class, "1234567890.0"),
                arguments(URI.class, "https:\\www.example.com"),
                arguments(Charset.class, "UTF-18"),
                arguments(java.time.Duration.class, "PTT1H2M3S"),
                arguments(Instant.class, "2023-12-28T10:115:30Z"),
                arguments(LocalDate.class, "2023-12-28T10:15:30"),
                arguments(LocalDateTime.class, "2023-12-28"),
                arguments(LocalTime.class, "PTT1H2M3S"),
                arguments(MonthDay.class, "--*02-29"),
                arguments(OffsetDateTime.class, "*2023-12-28T10:15:30-02:00"),
                arguments(OffsetTime.class, "*10:15:30-02:00"),
                arguments(Year.class, ".2023"),
                arguments(YearMonth.class, ".2023-12"),
                arguments(ZoneOffset.class, ".+02:00"),
                arguments(ZonedDateTime.class, "20.23-12-28T10:15:30+02:00[Europe/Moscow]"),
                arguments(TimeUnit.class, "SECOND"),
                arguments(AbstractChronology.class, "ISU"),
                arguments(AccessMode.class, "READS"),
                arguments(AclEntryFlag.class, "FILE_INHERITS"),
                arguments(AclEntryPermission.class, "READ_DATAS"),
                arguments(AclEntryType.class, "ALLOWS"),
                arguments(AddressingFeature.Responses.class, "ANONYMOUSES"),
                arguments(Authenticator.RequestorType.class, "PROXI"),
                arguments(Byte.class, "H"),
                arguments(ByteBuffer.class, "test"),
                arguments(Calendar.class, "2023-12-28T10:15:30Z"),
                arguments(CertPathValidatorException.BasicReason.class, "ALGORITHM_CONSTRAINEDED"),
                arguments(Character.class, "A"),
                arguments(Character.UnicodeScript.class, "LATINA"),
                arguments(ChronoField.class, "YEARS"),
                arguments(ChronoUnit.class, "DAY"),
                arguments(ClientInfoStatus.class, "REASON_UNKNOWEN"),
                arguments(CollationKey.class, "test"),
                arguments(Collector.Characteristics.class, "CONCURRENTS"),
                arguments(Component.BaselineResizeBehavior.class, "CONSTANT_ASCENTS"),
                arguments(CompoundName.class, "cn=John Doe,ou=People,dc=example,dc=com"),
                arguments(CRLReason.class, "UNSPECIFIEDY"),
                arguments(CryptoPrimitive.class, "MESSAGE_DIGESTED"),
                arguments(XmlNsForm.class, "QUALIFIEDS"),
                arguments(java.sql.Date.class, "2023-12-28 11:00"),
                arguments(DayOfWeek.class, "MONDAYS"),
                arguments(Desktop.Action.class, "BROWS"),
                arguments(Diagnostic.Kind.class, "ERROP"),
                arguments(Dialog.ModalExclusionType.class, "NO_EXCLUDES"),
                arguments(Dialog.ModalityType.class, "APPLICATION_MODALH"),
                arguments(DocumentationTool.Location.class, "DOCUMENTATION_OUTPUTS")
        );
    }
}