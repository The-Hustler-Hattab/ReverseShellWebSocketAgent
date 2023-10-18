package com.mtattab.reverseshell.service;

import com.mtattab.reverseshell.model.CommandRestOutput;

public interface Command {
    CommandRestOutput executeCommand(String command);

}
