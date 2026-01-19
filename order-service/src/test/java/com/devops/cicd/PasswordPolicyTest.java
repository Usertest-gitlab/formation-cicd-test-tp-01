package com.devops.cicd;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PasswordPolicyTest {

    @Test
    void containsEightCaracTest() { 
        assertTrue(PasswordPolicy.containsEightCarac("mot de passe"));
    }

    @Test
    void not_containsEightCaracTest() { 
        assertFalse(PasswordPolicy.containsEightCarac("mdp"));
    }

    @Test
    void containsMajTest() { 
        assertTrue(PasswordPolicy.containsMaj("Password"));
    }

    @Test
    void not_containsMajTest() { 
        assertFalse(PasswordPolicy.containsMaj("password"));
    }

    @Test
    void containsNumTest() { 
        assertTrue(PasswordPolicy.containsNum("Test 3"));
    }

    @Test
    void not_containsNumTest() { 
        assertFalse(PasswordPolicy.containsNum("Test trois"));
    }
    
    @Test
    void containsSpeCaracTest() { 
        assertTrue(PasswordPolicy.containsSpeCarac("Test n°4"));
    }
    
    @Test
    void not_containsSpeCaracTest() { 
        assertFalse(PasswordPolicy.containsSpeCarac("Test numero 4"));
    }
}
