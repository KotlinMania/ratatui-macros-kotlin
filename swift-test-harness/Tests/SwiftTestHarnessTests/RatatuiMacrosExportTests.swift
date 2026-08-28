import Testing
import RatatuiMacros

@Suite("RatatuiMacros Swift Export Tests")
struct RatatuiMacrosExportTests {
    @Test("Swift module loads cleanly")
    func testSwiftModuleLoads() {
        #expect(Bool(true), "RatatuiMacros swift module imported cleanly")
    }
}
