
-- Define Procedures for Journal Creation upon Income / Expense insertion
CREATE OR REPLACE FUNCTION public.create_journal_from_income()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
txId INTEGER := ceil(extract(epoch from now()));
debitId INTEGER := ceil(extract(epoch from now())) + 1;
creditId INTEGER := ceil(extract(epoch from now())) + 2;
BEGIN
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (txId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (debitId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (creditId);

    -- Insert Journal Entry
    -- For Income - Debit: Source of Fund, Credit: Income Account
    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (debitId, '1010100', (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description  FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (creditId, (SELECT coa_id FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    RETURN NEW;
END;
$function$


CREATE OR REPLACE FUNCTION public.create_journal_from_income()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
txId INTEGER := ceil(extract(epoch from now()));
debitId INTEGER := ceil(extract(epoch from now())) + 1;
creditId INTEGER := ceil(extract(epoch from now())) + 2;
BEGIN
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (txId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (debitId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (creditId);

    -- Insert Journal Entry
    -- For Income - Debit: Source of Fund, Credit: Income Account
    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (debitId, '1010100', (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description  FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (creditId, (SELECT coa_id FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    RETURN NEW;
END;
$function$

-- Create trigger to execute the function
CREATE TRIGGER create_journal_upon_income_insertion
  BEFORE INSERT
  ON financialreport_income
  FOR EACH ROW
  EXECUTE PROCEDURE create_journal_from_income();

CREATE TRIGGER create_journal_upon_expense_insertion
  BEFORE INSERT
  ON financialreport_expense
  FOR EACH ROW
  EXECUTE PROCEDURE create_journal_from_expense();