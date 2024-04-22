import unittest
from grammar import Grammar

class TestGrammar(unittest.TestCase):
    def setUp(self):
        self.g = Grammar()

    def test_remove_epsilon(self):
        P1_expected = {
            'S': ['bA', 'BC'],
            'A': ['a', 'aS', 'bCaCa', 'bCaC', 'bCa', 'bC', 'bA', 'b']
        }
        P1_actual = self.g.Remove_Epsilon()
        self.assertEqual(P1_actual, P1_expected)

    def test_eliminate_unit_prod(self):
        P2_expected = {
            'S': ['bA', 'BC'],
            'A': ['a', 'aS', 'bCaCa', 'bCaC', 'bCa', 'bC', 'bA', 'b'],
            'B': ['a', 'aS', 'bCaCa', 'bCaC', 'bCa', 'bC', 'bA', 'b']
        }
        P2_actual = self.g.Eliminate_Unit_Prod()
        self.assertEqual(P2_actual, P2_expected)

    def test_eliminate_inaccesible_symbols(self):
        P3_expected = {
            'S': ['bA', 'BC'],
            'A': ['a', 'aS', 'bCaCa', 'bCaC', 'bCa', 'bC', 'bA', 'b'],
            'B': ['a', 'aS', 'bCaCa', 'bCaC', 'bCa', 'bC', 'bA', 'b']
        }
        P3_actual = self.g.Eliminate_Inaccesible_Symbols()
        self.assertEqual(P3_actual, P3_expected)

    def test_remove_nonproductive(self):
        P4_expected = {
            'S': ['bA', 'BC'],
            'A': ['a', 'aS', 'bCaCa', 'bCaC', 'bCa', 'bC', 'bA', 'b'],
            'B': ['a', 'aS', 'bCaCa', 'bCaC', 'bCa', 'bC', 'bA', 'b']
        }
        P4_actual = self.g.Remove_Nonproductive()
        self.assertEqual(P4_actual, P4_expected)

    def test_chomsky_normal_form(self):
        P5_expected = {
            'S': ['bA', 'BC'],
            'A': ['a', 'DE', 'FG', 'FG'],
            'B': ['DH', 'FH', 'a', 'DE', 'FG', 'FG'],
            'C': ['AB'],
            'D': ['bC'],
            'E': ['aCa'],
            'F': ['b'],
            'G': ['aa'],
            'H': ['Aa']
        }
        P5_actual = self.g.Chomsky_Normal_Form()
        self.assertEqual(P5_actual, P5_expected)

if __name__ == '__main__':
    unittest.main()
