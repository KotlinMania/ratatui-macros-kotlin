# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 6/6 (100.0%)
- **Function parity:** 0/17 matched (target 53) — 0.0%
- **Class/type parity:** 0/0 matched — N/A
- **Combined symbol parity:** 0/17 matched (target 53) — 0.0%
- **Average inline-code cosine:** 0.31 (function body across 5 matched files)
- **Average documentation cosine:** 0.35 (doc text across 5 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 5 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. text

- **Target:** `ratatuimacros.Text`
- **Similarity:** 0.57
- **Dependents:** 1
- **Priority Score:** 1010104.2
- **Functions:** 0/1 matched (target 4)
- **Missing functions:** `text`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 2. row

- **Target:** `ratatuimacros.Row`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 70710.0
- **Functions:** 0/7 matched (target 4)
- **Missing functions:** `row_literal`, `row_empty`, `row_single_cell`, `row_repeated_cell`, `row_explicit_use_of_span_and_line`, `row_vec_count_syntax`, `multiple_rows`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/7 matched

### 3. line

- **Target:** `ratatuimacros.Line`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 70710.0
- **Functions:** 0/7 matched (target 4)
- **Missing functions:** `line_literal`, `line_raw_instead_of_literal`, `line_vec_count_syntax`, `line_vec_count_syntax_with_span`, `line_empty`, `line_single_span`, `line_repeated_span`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/7 matched

### 4. span

- **Target:** `ratatuimacros.Span [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20210.0
- **Functions:** 0/2 matched (target 23)
- **Missing functions:** `raw`, `styled`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Tests:** 0/2 matched

### 5. layout

- **Target:** `ratatuimacros.Layout [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 18)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 6. lib

- **Target:** `ratatuimacros.Lib`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 0.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

