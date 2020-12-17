import React, {Component} from "react";
import {withStyles} from "@material-ui/core/styles";
import SubmitInput from './SubmitInput';

const styles = theme => ({
    
});

class Solver extends Component {
    constructor() {
        super();
        this.state = {
            
        }
    }

    jsonEscape(str)  {
        return str.replace(/\n/g, "\\\\n").replace(/\r/g, "\\\\r").replace(/\t/g, "\\\\t");
    }

    async clickMe(state) {
        
    }

    render() {
        return (
            <div >
                <SubmitInput 
                    csvHandler={this.props.csvHandler} 
                    selectedCSVHandler={this.props.selectedCSVHandler}
                    numColHandler={this.props.numColHandler}
                    numCol={this.props.numCol}

                    csv={this.props.csv}
                    selectedCSV={this.props.selectedCSV}
                    startColHandler={this.props.startColHandler}
                    startCol={this.props.startCol}

                    endColHandler={this.props.endColHandler}
                    endCol={this.props.endCol}

                    initializedHandler= {this.props.initializedHandler} 
                    uploadedHandler= {this.props.uploadedHandler} 
                    generatedHandler= {this.props.generatedHandler}
                    solvedHandler= {this.props.solvedHandler}
                    initializedErrorHandler= {this.props.initializedErrorHandler} 
                    uploadedErrorHandler= {this.props.uploadedErrorHandler}
                    generatedErrorHandler= {this.props.generatedErrorHandler}
                    solvedErrorHandler= {this.props.solvedErrorHandler}

                    initialized={this.props.initialized}
                    uploaded={this.props.uploaded}
                    generated={this.props.generated}
                    solved={this.props.solved}
                    initializeError={this.props.initializeError}
                    uploadedError={this.props.uploadedError}
                    generatedError={this.props.generatedError}
                    solvedError={this.props.solvedError}

                    textAreaValue={this.props.textAreaValue}
                    values={this.props.values}
                    weights={this.props.weights}

                    textAreaValueHandler={this.props.textAreaValueHandler}
                    valuesHandler={this.props.valuesHandler}
                    weightsHandler={this.props.weightsHandler}
                />
            </div>
        )
    }
}

export default withStyles(styles) (Solver);
