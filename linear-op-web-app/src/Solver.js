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
                numColHanlder={this.props.numColHanlder}
                numCol={this.props.numCol}
                />
            </div>
        )
    }
}

export default withStyles(styles) (Solver);
